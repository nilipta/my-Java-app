package com.springapp.myapp.admin;

import java.util.Set;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.myapp.user.UserDto;
import com.springapp.myapp.user.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public List<UserDto> getUsers(@RequestParam(name="sort", required = false, defaultValue = "") String sortBy)   {
        if(!Set.of("name","email").contains(sortBy))
            sortBy = "name";
        return userService.getAllUsers(sortBy);
    } 
}
