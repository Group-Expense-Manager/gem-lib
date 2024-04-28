package pl.edu.agh.gem.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import pl.edu.agh.gem.headers.CustomHeaders.X_OAUTH_TOKEN_VALIDATED
import pl.edu.agh.gem.security.resolver.GemUserResolver

class GemUserResolverTest : ShouldSpec({

    val resolver = GemUserResolver()
    val parameter = mock<MethodParameter>()
    val mavContainer = mock<ModelAndViewContainer>()
    val webRequest = mock<NativeWebRequest>()
    val binderFactory = mock<WebDataBinderFactory>()

    should("GemUserResolver should resolve GemUser from request header") {
        // Given
        val user = GemUser("123", "example@op.pl")
        whenever(webRequest.getHeader(X_OAUTH_TOKEN_VALIDATED)).thenReturn(jacksonObjectMapper().writeValueAsString(user))

        // When
        val result = resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory)

        // Then
        result shouldBe user
    }
},)
