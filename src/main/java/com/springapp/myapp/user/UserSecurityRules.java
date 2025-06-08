package com.springapp.myapp.user;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import com.springapp.myapp.common.SecurityRules;

@Component
public class UserSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
            .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
            .requestMatchers("/h2-console/**").permitAll() // <-- Add this line
            .requestMatchers(HttpMethod.GET, "/api/users").hasRole(Role.ADMIN.name());
    }

}
