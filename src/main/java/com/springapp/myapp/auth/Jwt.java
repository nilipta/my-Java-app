package com.springapp.myapp.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import com.springapp.myapp.user.Role;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Jwt {
    private final Claims claims;
    private final SecretKey key;

    public boolean isExpired() {
        return (claims.getExpiration().before(new Date()));
    }

    public Long getUserId() {
        return Long.parseLong(claims.getSubject());
    }

    public Role getRole(){
        return Role.valueOf(claims.get("role").toString());
    }

    public String toString() {
        return Jwts.builder()
            .claims(claims)
            .signWith(key)
            .compact();
    }
}
