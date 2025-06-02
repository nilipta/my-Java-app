package com.springapp.myapp.user;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDto> getAllUsers(String sortBy){
        if(!Set.of("name", "email").contains(sortBy))
            sortBy = "name";
        return [];
    }
}
