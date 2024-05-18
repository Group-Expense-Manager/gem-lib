package pl.edu.agh.gem.validator

class ValidatorsException(val failedValidations: List<String>) : RuntimeException("Failed validations: ${failedValidations.joinToString(", ")}")
