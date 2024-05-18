package pl.edu.agh.gem.validator

class ValidatiorList<T : DataWrapper>(private val validations: List<BaseValidator<T>>) {

    fun getFailedValidations(dataWrapper: T): List<String> {
        return validations
            .map { it.apply(dataWrapper) }
            .filterIsInstance<FailureCheckResult>()
            .map { it.name }
    }

    companion object {
        fun <T : DataWrapper> listOf(vararg validations: BaseValidator<T>): ValidatiorList<T> {
            return ValidatiorList(validations.toList())
        }
    }
}
