package pl.edu.agh.gem.error

import org.springframework.web.bind.MethodArgumentNotValidException
import pl.edu.agh.gem.validator.ValidatorsException

data class SimpleErrorsHolder(
    val errors: List<SimpleError>,
)

fun SimpleError.toSimpleErrorHolder() = SimpleErrorsHolder(listOf(this))

data class SimpleError(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
    val path: String? = null,
    val userMessage: String? = null,
)

fun SimpleError.withCode(code: String?) = this.copy(code = code)

fun SimpleError.withMessage(message: String?) = this.copy(message = message)

fun SimpleError.withDetails(details: String?) = this.copy(details = details)

fun SimpleError.withPath(path: String?) = this.copy(path = path)

fun SimpleError.withUserMessage(userMessage: String?) = this.copy(userMessage = userMessage)

fun handleError(exception: Exception): SimpleErrorsHolder =
    SimpleError()
        .withCode(exception.javaClass.simpleName)
        .withMessage(exception.message)
        .withDetails(exception.javaClass.simpleName)
        .withUserMessage(exception.message)
        .toSimpleErrorHolder()

fun handleValidatorsException(exception: ValidatorsException): SimpleErrorsHolder {
    val errors =
        exception.failedValidations
            .map { error ->
                SimpleError()
                    .withCode("VALIDATOR_ERROR")
                    .withMessage(error)
            }
    return SimpleErrorsHolder(errors)
}

fun handleNotValidException(exception: MethodArgumentNotValidException): SimpleErrorsHolder {
    val errors =
        exception.bindingResult.fieldErrors
            .map { error ->
                SimpleError()
                    .withCode("VALIDATION_ERROR")
                    .withDetails(error.field)
                    .withUserMessage(error.defaultMessage)
                    .withMessage(error.defaultMessage)
            }
    return SimpleErrorsHolder(errors)
}
