package core.gammiechat.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${web.client.baseurl}") private val applicationUrl: String,
){

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl(applicationUrl)
            .build()
    }
}