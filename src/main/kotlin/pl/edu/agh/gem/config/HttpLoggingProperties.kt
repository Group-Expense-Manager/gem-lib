package pl.edu.agh.gem.config

import org.springframework.boot.context.properties.ConfigurationProperties
import pl.edu.agh.gem.helper.logger.LoggerType
import pl.edu.agh.gem.helper.logger.LoggerType.INFO

@ConfigurationProperties("gem.http.logging")
data class HttpLoggingProperties(
    val enabled: Boolean = true,
    val loggerLevel: LoggerType = INFO,
    val logPayload: Boolean = true,
    val maxPayloadLength: Long = 5120,
    val logHeaders: Boolean = true,
    val logClient: Boolean = true,
    val excludeHeaders: List<String> = listOf("Authorization"),
    val excludeUrlPrefixes: List<String> = listOf("/actuator/health"),
)
