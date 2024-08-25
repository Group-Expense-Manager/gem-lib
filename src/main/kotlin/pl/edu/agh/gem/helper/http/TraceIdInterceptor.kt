package pl.edu.agh.gem.helper.http

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import pl.edu.agh.gem.headers.CustomHeaders.TRACE_ID

@Component
class TraceIdInterceptor : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
    ): ClientHttpResponse {
        val traceId = TraceIdContextHolder.getTraceId()
        request.headers.add(TRACE_ID, traceId)
        return execution.execute(request, body)
    }
}
