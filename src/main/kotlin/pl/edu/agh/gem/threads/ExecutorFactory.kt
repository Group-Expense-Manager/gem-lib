package pl.edu.agh.gem.threads

import org.apache.commons.lang3.concurrent.BasicThreadFactory
import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor

class ExecutorFactory {
    fun createExecutor(config: ExecutorConfig): Executor = createThreadPoolExecutor(config)

    private fun createThreadPoolExecutor(executorConfig: ExecutorConfig): ThreadPoolExecutor =
        ThreadPoolExecutor(
            executorConfig.corePoolSize,
            executorConfig.maxPoolSize,
            executorConfig.keepAliveTime,
            executorConfig.timeUnit,
            LinkedBlockingQueue(executorConfig.taskQueueSize),
            createThreadFactory(
                executorConfig.threadPoolName,
                executorConfig.daemonThreads,
                executorConfig.threadPriority,
                executorConfig.uncaughtExceptionHandler,
            ),
            executorConfig.rejectedExecutionHandler,
        ).apply { if (executorConfig.preRestartAllCoreThreads) prestartAllCoreThreads() }

    private fun createThreadFactory(
        threadPoolName: String,
        daemonThreads: Boolean,
        threadPriority: Int?,
        handler: UncaughtExceptionHandler,
    ): ThreadFactory =
        BasicThreadFactory
            .Builder()
            .namingPattern("$threadPoolName-%d")
            .uncaughtExceptionHandler(handler)
            .daemon(daemonThreads)
            .apply { if (threadPriority != null) priority(threadPriority) }
            .build()
}
