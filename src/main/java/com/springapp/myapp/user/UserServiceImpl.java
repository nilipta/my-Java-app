package com.springapp.myapp.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


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

    @Override
    public UserDto registerUser(RegisterUserRequest request) throws UserAlreadyExistsException{
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        if(userRepository.existsUserByEmail(request.getEmail())){
            throw new UserAlreadyExistsException();
        }

        var user = userMapper.update(request);
        user.setRole(Role.USER);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
}
