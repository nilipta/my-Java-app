package com.springapp.myapp.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // return to login end point
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = authHeader.replace("Bearer ", "");
        var jwt = jwtService.parse(token);
        if (jwt == null || jwt.isExpired()) {
            filterChain.doFilter(request, response);
            return;
        }

        var authentication = new UsernamePasswordAuthenticationToken(
            jwt.getUserId(),
            //jwtTokenService.getEmailFromToken(token), // user object, either user, username, email etc.
            null,
            // null // roles and permissions, for authenticated users. No need here before implementing the role-based access
            /*
            * authorities:
            * 1. Roles (ADMIN, USER, etc.), roles have to start with "ROLE_" + role_name as the example below, which permission no need
            * 2. Permissions(e.g. ISSUE_REFUND)
            */
            List.of(new SimpleGrantedAuthority("ROLE_" + jwt.getRole().name()))
        );
        // add request metadata into the authentication details
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        // set it for further usage, e.g., to access the current user
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
