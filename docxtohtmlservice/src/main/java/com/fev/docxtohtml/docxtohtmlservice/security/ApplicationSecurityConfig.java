package com.fev.docxtohtml.docxtohtmlservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.headers(
            headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
