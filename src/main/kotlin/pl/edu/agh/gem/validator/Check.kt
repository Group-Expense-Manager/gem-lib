package pl.edu.agh.gem.validator

class Check<T : DataWrapper>(
    private val name: CheckName,
    private val checker: (T) -> Boolean,
) {
    fun apply(data: T): CheckResult {
        return if (checker.invoke(data)) {
            SuccessCheckResult
        } else {
            FailureCheckResult(name)
        }
    }
}

typealias CheckName = String

interface DataWrapper

sealed class CheckResult
data object SuccessCheckResult : CheckResult()
data class FailureCheckResult(val name: String) : CheckResult()
