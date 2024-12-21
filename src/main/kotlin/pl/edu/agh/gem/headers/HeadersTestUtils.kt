package pl.edu.agh.gem.headers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import pl.edu.agh.gem.headers.CustomHeaders.X_OAUTH_TOKEN_VALIDATED
import pl.edu.agh.gem.media.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1
import pl.edu.agh.gem.security.GemUser

object HeadersTestUtils {
    fun ResponseDefinitionBuilder.withValidatedUser(user: GemUser) =
        withHeader(X_OAUTH_TOKEN_VALIDATED, jacksonObjectMapper().writeValueAsString(user))

    fun ResponseDefinitionBuilder.withAppContentType(type: String = APPLICATION_JSON_INTERNAL_VER_1) =
        withHeader(
            CONTENT_TYPE,
            type,
        )

    fun ResponseDefinitionBuilder.withAppAcceptType(type: String = APPLICATION_JSON_INTERNAL_VER_1) =
        withHeader(
            ACCEPT,
            type,
        )
}
