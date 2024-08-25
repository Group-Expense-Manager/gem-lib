package pl.edu.agh.gem.helper.logger

import mu.KotlinLogging
import pl.edu.agh.gem.helper.logger.LoggerType.DEBUG
import pl.edu.agh.gem.helper.logger.LoggerType.ERROR
import pl.edu.agh.gem.helper.logger.LoggerType.INFO
import pl.edu.agh.gem.helper.logger.LoggerType.TRACE
import pl.edu.agh.gem.helper.logger.LoggerType.WARN

object LoggerHelper {
    fun getLogger(level: LoggerType, function: () -> String) {
        val logger = KotlinLogging.logger { }
        return when (level) {
            TRACE -> logger.trace { function() }
            DEBUG -> logger.debug { function() }
            INFO -> logger.info { function() }
            WARN -> logger.warn { function() }
            ERROR -> logger.error { function() }
        }
    }
}
