package com.springapp.myapp.auth;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.springapp.myapp.user.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtServiceImpl implements JwtService{
    private final JwtConfig jwtConfig;

    @Override
    public Jwt parse(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    @Override
    public Jwt generateAccessToken(UserDto userDto) {
        return generateToken(userDto, jwtConfig.getAccessTokenExpiration());
    }
  
  
    @Override
    public Jwt generateRefreshToken(UserDto userDto){
        return generateToken(userDto, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(UserDto userDto, long tokenExpiration) {
        var token = Jwts.claims()
                .subject(userDto.getId().toString())
                .add("name", userDto.getName())
                .add("email", userDto.getEmail())
                .add("role", userDto.getRole())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 1000 * tokenExpiration))
                .build();
        return new Jwt(token, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
}
