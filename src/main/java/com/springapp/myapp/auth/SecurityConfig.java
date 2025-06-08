package com.springapp.myapp.auth;

import com.springapp.myapp.common.SecurityRules;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    // with @Component annotation on each SecurityRules classes, Spring will scan and register them automatically and add them in this list.
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private List<SecurityRules> securityRules;

        @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder( passwordEncoder() );
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http.sessionManagement(c-> c.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS
        )).csrf(AbstractHttpConfigurer::disable)
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // <-- Add this line
        .authorizeHttpRequests(c -> {
            securityRules.forEach(securityRules -> securityRules.configure(c));
            c.anyRequest().authenticated();
        })
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(c -> {
            c.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
            c.accessDeniedHandler(((request, response, accessDeniedException) ->
                    response.setStatus(HttpStatus.FORBIDDEN.value())));
        });
        return http.build();
    }
    
}
