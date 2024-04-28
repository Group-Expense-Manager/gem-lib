package pl.edu.agh.gem.error

data class SimpleErrorsHolder(val errors: List<SimpleError>)

fun SimpleError.toSimpleErrorHolder() =
    SimpleErrorsHolder(listOf(this))

data class SimpleError(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
    val path: String? = null,
    val userMessage: String? = null,
)

fun SimpleError.withCode(code: String?) =
    this.copy(code = code)

fun SimpleError.withMessage(message: String?) =
    this.copy(message = message)

fun SimpleError.withDetails(details: String?) =
    this.copy(details = details)

fun SimpleError.withPath(path: String?) =
    this.copy(path = path)

fun SimpleError.withUserMessage(userMessage: String?) =
    this.copy(userMessage = userMessage)
