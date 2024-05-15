package pl.edu.agh.gem.validator

class ValidatorsException(val failedValidations: List<CheckName>) : RuntimeException("Failed validations: ${failedValidations.joinToString(", ")}")
