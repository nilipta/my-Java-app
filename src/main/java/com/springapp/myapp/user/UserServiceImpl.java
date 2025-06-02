package com.springapp.myapp.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserDto> getAllUsers(String sortBy){
        if(!Set.of("name", "email").contains(sortBy))
            sortBy = "name";
        return userRepository
            .findAll(Sort.by(sortBy))
            .stream()
            .map(userMapper::toDto)
            .toList();
    }

    @Override
    public UserDto getUserById(Long id){
        var user = userRepository.findById(id).orElseThrow(
            UserNotFoundException::new
        );
        return userMapper.toDto(user);
    }
}
