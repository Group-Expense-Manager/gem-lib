package pl.edu.agh.gem.helper.http

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import pl.edu.agh.gem.config.HttpLoggingProperties
import pl.edu.agh.gem.helper.logger.LoggerHelper.getLogger
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets.UTF_8

@Component
class LoggingInterceptor(
    private val properties: HttpLoggingProperties,
) : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
    ): ClientHttpResponse {
        val url = request.uri.toString()
        val shouldLog = properties.excludeUrlPrefixes.none { url.startsWith(it) }

        if (shouldLog) {
            val logMessage = StringBuilder("Client HTTP Request - ${request.method} ${request.uri.path}\n")

            if (properties.logHeaders) {
                val headers = logHeadersString(request.headers)
                logMessage.append("Headers:\n\t$headers\n")
            }

            if (properties.logPayload) {
                val payload = logPayloadString(body)
                logMessage.append("Payload: $payload\n")
            }

            getLogger(properties.loggerLevel) { logMessage.toString() }
        }

        val response = execution.execute(request, body)
        val wrappedResponse = CachedBodyClientHttpResponse(response)

        if (shouldLog) {
            val responseBody = wrappedResponse.body

            val logMessage = StringBuilder("Client HTTP Response - ${request.method} ${request.uri.path} Status: ${wrappedResponse.statusCode}\n")

            if (properties.logHeaders) {
                val headers = logHeadersString(wrappedResponse.headers)
                logMessage.append("Headers:\n\t$headers\n")
            }

            if (properties.logPayload) {
                val payload = logPayloadString(responseBody)
                logMessage.append("Payload: $payload\n")
            }

            getLogger(properties.loggerLevel) { logMessage.toString() }
        }

        return wrappedResponse
    }

    private fun logHeadersString(headers: HttpHeaders): String {
        return headers
                .filterKeys { it !in properties.excludeHeaders }
                .map { "${it.key}: ${it.value.joinToString()}" }
                .joinToString("\n\t")
    }

    private fun logPayloadString(payload: ByteArray): String {
        return payload.toString(UTF_8).take(properties.maxPayloadLength.toInt())
    }

    private fun logPayloadString(responseBody: InputStream): String {
        BufferedReader(InputStreamReader(responseBody, UTF_8)).use { reader ->
            return reader.readText().take(properties.maxPayloadLength.toInt())
        }
    }

    private class CachedBodyClientHttpResponse(
        private val delegate: ClientHttpResponse
    ) : ClientHttpResponse by delegate {
        private val bodyBytes: ByteArray = delegate.body.readAllBytes()

        override fun getBody(): InputStream {
            return ByteArrayInputStream(bodyBytes)
        }

        override fun close() {
            delegate.close()
        }
    }
}
