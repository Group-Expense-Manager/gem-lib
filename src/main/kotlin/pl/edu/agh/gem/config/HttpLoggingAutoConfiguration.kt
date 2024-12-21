package pl.edu.agh.gem.config

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@AutoConfiguration
@ConditionalOnProperty(prefix = "gem.http.logging", name = ["enabled"], havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(HttpLoggingProperties::class)
class HttpLoggingAutoConfiguration(
    private val properties: HttpLoggingProperties,
) {
    @Bean
    @Order(1)
    fun requestLoggingFilter(): OncePerRequestFilter = RequestLoggingFilter(properties)

    @Bean
    @Order(1)
    fun myCustomFilterRegistration(): FilterRegistrationBean<RequestLoggingFilter> {
        val registrationBean = FilterRegistrationBean<RequestLoggingFilter>()
        registrationBean.filter = RequestLoggingFilter(properties)
        return registrationBean
    }
}
