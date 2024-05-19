package pl.edu.agh.gem.validator

class ValidatorList<T : DataWrapper>(private val validators: List<BaseValidator<T>>) {

    fun getFailedValidations(dataWrapper: T): List<String> {
        return validators
            .flatMap { validators -> validators.apply(dataWrapper) }
            .filterIsInstance<FailureCheckResult>()
            .map { it.name }
    }

    companion object {
        fun <T : DataWrapper> validatorsOf(vararg validations: BaseValidator<T>): ValidatorList<T> {
            return ValidatorList(validations.toList())
        }
    }
}
