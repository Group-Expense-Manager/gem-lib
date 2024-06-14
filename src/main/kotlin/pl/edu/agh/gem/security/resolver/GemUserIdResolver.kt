package pl.edu.agh.gem.security.resolver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import pl.edu.agh.gem.headers.CustomHeaders.X_OAUTH_TOKEN_VALIDATED
import pl.edu.agh.gem.security.GemUser
import pl.edu.agh.gem.security.GemUserId

class GemUserIdResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == String::class.java && parameter.hasParameterAnnotation(GemUserId::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val authHeader = webRequest.getHeader(X_OAUTH_TOKEN_VALIDATED) ?: throw MissingTokenException()
        val user = jacksonObjectMapper().readValue(authHeader, GemUser::class.java)
        return user.id
    }
}
