package pl.edu.agh.gem.helper.http

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Component
class GemRestTemplateFactory(
    private val loggingInterceptor: LoggingInterceptor,
    private val traceIdInterceptor: TraceIdInterceptor,
) {
    fun builder(): GemRestTemplateBuilder {
        return GemRestTemplateBuilder(loggingInterceptor, traceIdInterceptor)
    }
}

class GemRestTemplateBuilder(
    private val loggingInterceptor: LoggingInterceptor,
    private val traceIdInterceptor: TraceIdInterceptor,
) {
    private val baseRestTemplate = RestTemplateBuilder()

    fun withConnectTimeout(connectTimeout: Duration): GemRestTemplateBuilder {
        baseRestTemplate.setConnectTimeout(connectTimeout)
        return this
    }

    fun withReadTimeout(readTimeout: Duration): GemRestTemplateBuilder {
        baseRestTemplate.setReadTimeout(readTimeout)
        return this
    }

    fun build(): RestTemplate {
        return baseRestTemplate
            .additionalInterceptors(traceIdInterceptor, loggingInterceptor)
            .build()
    }
}
