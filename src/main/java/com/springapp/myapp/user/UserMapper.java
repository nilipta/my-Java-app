package com.springapp.myapp.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User update(RegisterUserRequest request);
    void update(@MappingTarget User user, UpdateUserRequest request);
    // mapping methods
    UserDto toDto(User user);
}
