package pl.edu.agh.gem.security

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@Target(VALUE_PARAMETER)
@Retention(RUNTIME)
annotation class GemAuthenticatedUser

@Target(VALUE_PARAMETER)
@Retention(RUNTIME)
annotation class GemUserId

@Target(VALUE_PARAMETER)
@Retention(RUNTIME)
annotation class GemUserEmail
