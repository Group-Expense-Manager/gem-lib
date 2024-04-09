package pl.edu.agh.gem.error

data class SimpleErrorsHolder(val errors: List<SimpleError>) {
    companion object {
        fun fromError(error: SimpleError) =
            SimpleErrorsHolder(listOf(error))
    }
}

data class SimpleError(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
    val path: String? = null,
    val userMessage: String? = null
) {
    fun withCode(code: String?) =
        this.copy(code = code)

    fun withMessage(message: String?) =
        this.copy(message = message)

    fun withDetails(details: String?) =
        this.copy(details = details)

    fun withPath(path: String?) =
        this.copy(path = path)

    fun withUserMessage(userMessage: String?) =
        this.copy(userMessage = userMessage)
}
