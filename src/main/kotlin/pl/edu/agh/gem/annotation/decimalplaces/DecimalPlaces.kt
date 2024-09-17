package pl.edu.agh.gem.annotation.decimalplaces

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DecimalPlacesValidator::class])
annotation class DecimalPlaces(
    val message: String = "Invalid number of decimal places",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = [],
    val max: Int,
)
