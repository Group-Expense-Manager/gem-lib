package pl.edu.agh.gem.annotation.nullorpattern

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NullOrPatternValidator::class])
annotation class NullOrPattern(
    val message: String = "{javax.validation.constraints.NullOrUsernamePattern.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = [],
    val pattern: String,
)
