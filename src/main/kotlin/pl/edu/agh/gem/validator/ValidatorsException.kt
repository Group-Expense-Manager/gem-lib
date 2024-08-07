package pl.edu.agh.gem.validator

open class ValidatorsException(val failedValidations: List<String>) : RuntimeException("Failed validations: ${failedValidations.joinToString(", ")}")
