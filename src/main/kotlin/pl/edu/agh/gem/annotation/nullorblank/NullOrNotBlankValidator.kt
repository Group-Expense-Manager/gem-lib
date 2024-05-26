package pl.edu.agh.gem.annotation.nullorblank

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NullOrNotBlankValidator : ConstraintValidator<NullOrNotBlank?, String?> {

    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return value == null || value.trim { it <= ' ' }.isNotEmpty()
    }
}
