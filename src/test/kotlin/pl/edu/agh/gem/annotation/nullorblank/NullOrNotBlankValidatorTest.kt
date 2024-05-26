package pl.edu.agh.gem.annotation.nullorblank

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import jakarta.validation.ConstraintValidatorContext
import org.mockito.kotlin.mock

class NullOrNotBlankValidatorTest : ShouldSpec({
    val constraintValidatorContext = mock<ConstraintValidatorContext>()
    should("accept null value") {
        // given
        val nullString: String? = null
        val validator = NullOrNotBlankValidator()

        // given
        val result = validator.isValid(nullString, constraintValidatorContext)

        // then
        result shouldBe true
    }

    should("accept not blank value") {
        // given
        val string = "value"
        val validator = NullOrNotBlankValidator()

        // given
        val result = validator.isValid(string, constraintValidatorContext)

        // then
        result shouldBe true
    }

    should("not accept not null and blank value") {
        // given
        val nullString = ""
        val validator = NullOrNotBlankValidator()

        // given
        val result = validator.isValid(nullString, constraintValidatorContext)

        // then
        result shouldBe false
    }
},)
