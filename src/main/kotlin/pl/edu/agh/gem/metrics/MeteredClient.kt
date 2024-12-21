package pl.edu.agh.gem.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MeteredClient

@Aspect
@Component
class MeteredClientAspect(
    @Autowired private val meterRegistry: MeterRegistry,
) {
    @Around("@within(pl.edu.agh.gem.metrics.MeteredClient)")
    fun meter(joinPoint: ProceedingJoinPoint): Any? {
        val method =
            joinPoint.signature as? MethodSignature
                ?: throw IllegalArgumentException("Join point signature is not a MethodSignature")
        val className = method.method.declaringClass.simpleName
        val methodName = method.method.name
        val tags =
            listOf(
                Tag.of(CLIENT_TAG, className),
                Tag.of(HANDLER_TAG, methodName),
            )

        val counter =
            Counter
                .builder(METRIC_COUNTER_NAME)
                .tags(tags)
                .register(meterRegistry)

        val timer =
            Timer
                .builder(METRIC_NAME)
                .tags(tags)
                .publishPercentiles(*PERCENTILES)
                .register(meterRegistry)

        return try {
            timer.recordCallable { joinPoint.proceed() }.also {
                counter.increment()
            }
        } catch (e: Exception) {
            println("Exception occurred during method execution: ${e.message}")
            throw e
        }
    }

    companion object {
        const val METRIC_NAME = "metered.client"
        const val METRIC_COUNTER_NAME = "metered.client.count"
        const val CLIENT_TAG = "client"
        const val HANDLER_TAG = "handler"
        val PERCENTILES = doubleArrayOf(0.50, 0.90, 0.99, 0.999)
    }
}
