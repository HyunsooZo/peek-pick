package com.peekpick.shared.configuration

import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class HttpConfig {
    @Bean
    fun restClient(configurer: RestClientBuilderConfigurer): RestClient {
        return RestClient.builder().build()
    }
}
