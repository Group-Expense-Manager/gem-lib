package pl.edu.agh.gem.headers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import pl.edu.agh.gem.headers.CustomHeaders.X_OAUTH_TOKEN_VALIDATED
import pl.edu.agh.gem.media.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1
import pl.edu.agh.gem.security.GemUser

object HeadersUtils {

    fun HttpHeaders.withValidatedUser(user: GemUser) =
        apply {
            add(X_OAUTH_TOKEN_VALIDATED, jacksonObjectMapper().writeValueAsString(user))
        }

    fun HttpHeaders.withAppContentType(type: String = APPLICATION_JSON_INTERNAL_VER_1) =
        apply {
            add(CONTENT_TYPE, type)
        }

    fun HttpHeaders.withAppAcceptType(type: String = APPLICATION_JSON_INTERNAL_VER_1) =
        apply {
            add(ACCEPT, type)
        }
}
