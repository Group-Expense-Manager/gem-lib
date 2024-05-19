package pl.edu.agh.gem.validator

abstract class BaseValidator<T : DataWrapper> {
    protected abstract val checks: List<Check<T>>

    fun apply(dataWrapper: T) =
        checks.map { it.apply(dataWrapper) }
}
