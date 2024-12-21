package pl.edu.agh.gem.annotation.decimalplaces

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.math.BigDecimal
import kotlin.properties.Delegates

class DecimalPlacesValidator : ConstraintValidator<DecimalPlaces, BigDecimal> {
    private var maxDecimalPlaces by Delegates.notNull<Int>()

    override fun initialize(constraintAnnotation: DecimalPlaces) {
        this.maxDecimalPlaces = constraintAnnotation.max
    }

    override fun isValid(
        value: BigDecimal,
        context: ConstraintValidatorContext,
    ): Boolean = value.scale() <= maxDecimalPlaces
}
