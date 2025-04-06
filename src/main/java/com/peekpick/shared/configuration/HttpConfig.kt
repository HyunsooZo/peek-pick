package com.peekpick.shared.configuration

import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class HttpConfig {
    @Bean
    fun restClient(configurer: RestClientBuilderConfigurer): RestClient {
        val builder = configurer.configure(RestClient.builder())
        return builder
            .baseUrl("https://your-default-base-url.com")
            .build()
    }
}
