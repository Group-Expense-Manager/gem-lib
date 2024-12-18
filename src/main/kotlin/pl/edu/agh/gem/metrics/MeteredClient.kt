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
    @Autowired private val meterRegistry: MeterRegistry
) {

    @Around("@within(pl.edu.agh.gem.metrics.MeteredClient)")
    fun meter(joinPoint: ProceedingJoinPoint) {
        val method = joinPoint.signature as MethodSignature
        val className = method.method.declaringClass.simpleName
        val methodName = method.method.name
        val tags = listOf(
                Tag.of("client", className),
                Tag.of("handler", methodName)
        )

        val counter = Counter.builder("metered.client.count")
                .tags(tags)
                .register(meterRegistry)

        val timer = Timer.builder("metered.client")
                .tags(tags)
                .publishPercentiles(0.50, 0.90, 0.99, 0.999)
                .register(meterRegistry)

        try {
            timer.recordCallable { joinPoint.proceed() }
            counter.increment()
        } catch (e: Exception) {
            throw e
        }
    }
}
