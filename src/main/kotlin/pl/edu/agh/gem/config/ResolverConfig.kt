
package pl.edu.agh.gem.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pl.edu.agh.gem.security.resolver.GemEmailResolver
import pl.edu.agh.gem.security.resolver.GemUserIdResolver
import pl.edu.agh.gem.security.resolver.GemUserResolver

@Configuration
class ResolverConfig : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(GemUserResolver())
        resolvers.add(GemUserIdResolver())
        resolvers.add(GemEmailResolver())
    }
}
