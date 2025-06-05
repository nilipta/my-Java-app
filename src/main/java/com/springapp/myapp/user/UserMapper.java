package com.springapp.myapp.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // mapping methods
       UserDto toDto(User user);
}
// import org.mapstruct.Mapper;

// @Mapper(componentModel = "spring")
// public interface UserMapper {
//     // User toEntity()

//     UserDto toDto(User user);
// }
