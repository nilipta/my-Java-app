package com.springapp.myapp.auth;

import com.springapp.myapp.user.UserDto;

public interface JwtService {
    Jwt generateAccessToken(UserDto userDto);
    Jwt generateRefreshToken(UserDto userDto);
    Jwt parse(String token);
}
