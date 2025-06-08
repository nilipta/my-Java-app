package com.springapp.myapp.user;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        var user = userRepository
            .findByEmail(username)
            .orElseThrow(() -> new BadCredentialsException("user not found"));

        return new User(
            user.getEmail(),
            user.getPassword(),
            Collections.emptyList()
        );
    }
}
