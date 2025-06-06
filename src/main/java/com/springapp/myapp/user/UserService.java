package com.springapp.myapp.user;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(String sortBy);
    UserDto getUserById(Long id);
    UserDto registerUser(RegisterUserRequest request) throws IllegalArgumentException;
}
