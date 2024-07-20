package pl.edu.agh.gem.threads

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExecutorProperties {

    @Bean
    fun executorFactory(): ExecutorFactory {
        return ExecutorFactory()
    }
}
