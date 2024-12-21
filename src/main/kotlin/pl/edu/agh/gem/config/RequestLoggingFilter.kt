package pl.edu.agh.gem.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.edu.agh.gem.headers.CustomHeaders.TRACE_ID
import pl.edu.agh.gem.helper.http.CustomHttpServletRequestWrapper
import pl.edu.agh.gem.helper.http.CustomHttpServletResponseWrapper
import pl.edu.agh.gem.helper.http.TraceIdContextHolder
import pl.edu.agh.gem.helper.http.TraceIdGenerator
import pl.edu.agh.gem.helper.logger.LoggerHelper.getLogger

@Component
@Order(1)
class RequestLoggingFilter(
    private val properties: HttpLoggingProperties,
) : OncePerRequestFilter(),
    Ordered {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val wrappedRequest = CustomHttpServletRequestWrapper(request)
        val wrappedResponse = CustomHttpServletResponseWrapper(response)

        val traceId =
            request.getHeader(TRACE_ID) ?: run {
                TraceIdGenerator.generateTraceId()
            }
        response.setHeader(TRACE_ID, traceId)
        TraceIdContextHolder.setTraceId(traceId)

        val requestURI = wrappedRequest.requestURI
        val shouldLog = properties.excludeUrlPrefixes.none { prefix -> requestURI.startsWith(prefix) }

        if (shouldLog) {
            val logMessage =
                StringBuilder(
                    "Endpoint HTTP Request - ${wrappedRequest.method} ${wrappedRequest.requestURI}\n",
                )

            if (properties.logClient) {
                logMessage.append("Client IP: ${wrappedRequest.remoteAddr}\n")
            }

            if (properties.logHeaders) {
                val headers =
                    wrappedRequest.headerNames
                        .toList()
                        .filterNot { it in properties.excludeHeaders }
                        .joinToString("\n\t") { "$it: ${wrappedRequest.getHeader(it)}" }
                logMessage.append("Headers:\n\t$headers\n")
                logMessage.append("Trace ID: $traceId\n")
            }

            if (properties.logPayload) {
                val payload =
                    wrappedRequest.inputStream
                        .bufferedReader()
                        .use { it.readText() }
                        .take(properties.maxPayloadLength.toInt())
                logMessage.append("Payload: $payload\n")
            }

            getLogger(properties.loggerLevel) { logMessage.toString() }
        }

        filterChain.doFilter(wrappedRequest, wrappedResponse)

        if (shouldLog) {
            val responseBody = wrappedResponse.getResponsePayload()
            val responseLogMessage =
                StringBuilder(
                    "Endpoint HTTP Response - ${request.method} ${request.requestURI} Status: ${wrappedResponse.status}\n",
                )

            if (properties.logHeaders) {
                val headers =
                    response.headerNames
                        .toList()
                        .joinToString("\n\t") { "$it: ${response.getHeader(it)}" }
                responseLogMessage.append("Headers:\n\t$headers\n")
            }

            responseLogMessage.append("Payload: $responseBody\n")

            getLogger(properties.loggerLevel) { responseLogMessage.toString() }
        }

        wrappedResponse.writeOutputStream()
    }

    override fun getOrder(): Int = HIGHEST_PRECEDENCE
}
