package com.minlink.minlink;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.minlink.minlink.constants.Constants.CREATE_ENDPOINT;
import static com.minlink.minlink.constants.Constants.USER_ENDPOINT;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.GET, "/", "/error", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, CREATE_ENDPOINT).authenticated()
                        .requestMatchers(HttpMethod.GET, USER_ENDPOINT).authenticated()
//                        .requestMatchers(HttpMethod.GET, "/oauth2/authorization/github").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();
    }

}