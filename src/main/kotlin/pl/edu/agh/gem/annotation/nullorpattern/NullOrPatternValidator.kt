package pl.edu.agh.gem.annotation.nullorpattern

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Pattern

class NullOrPatternValidator : ConstraintValidator<NullOrPattern, String> {
    private lateinit var pattern: Pattern

    override fun initialize(constraintValidator: NullOrPattern) {
        pattern = Pattern.compile(constraintValidator.pattern)
    }

    override fun isValid(
        value: String?,
        constraintValidatorContext: ConstraintValidatorContext,
    ): Boolean = value == null || pattern.matcher(value).matches()
}
