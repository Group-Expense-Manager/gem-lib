package pl.edu.agh.gem.threads

import io.github.oshai.kotlinlogging.KotlinLogging
import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

private val logger = KotlinLogging.logger { }

data class ExecutorConfig(
    val threadPoolName: String,
    val corePoolSize: Int,
    val maxPoolSize: Int,
    val taskQueueSize: Int,
    val keepAliveTime: Long = 60,
    val timeUnit: TimeUnit = SECONDS,
    val daemonThreads: Boolean = true,
    val threadPriority: Int? = null,
    val rejectedExecutionHandler: RejectedExecutionHandler = AbortPolicy(),
    val uncaughtExceptionHandler: UncaughtExceptionHandler =
        UncaughtExceptionHandler { t, e ->
            logger.error(e) { "Exception in thread $t" }
        },
    val preRestartAllCoreThreads: Boolean = true,
)
