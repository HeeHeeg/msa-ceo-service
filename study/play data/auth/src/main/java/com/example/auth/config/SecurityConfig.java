package com.example.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    //시큐리티 config 등록
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.sessionManagement(
                configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        security.authenticationProvider(authenticationProvider);
        security.addFilterBefore(jwtAuthenticationFilter
                , UsernamePasswordAuthenticationFilter.class);

        //url열어주기
        security.authorizeHttpRequests(req ->
                req.requestMatchers(
                        //여기에 url등록하면 된다.
                        AntPathRequestMatcher.antMatcher("/api/v1/auth/login"),
                        AntPathRequestMatcher.antMatcher("/api/v1/auth/signup")
                )
                        .permitAll() // 모두 접근 가능하게하는것.
                        .anyRequest().authenticated() // login, signup만 접근할 수 있도록?

        );
        return security.build();
    }
}
