package com.ecommerce.user.service.impl;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.ResourceAlreadyExistsException;
import com.ecommerce.user.exception.ResourceNotFoundException;
import com.ecommerce.user.mapper.AuthMapper;
import com.ecommerce.user.mapper.UserMapper;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.UserService;
import com.ecommerce.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    private AuthMapper authMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserCreateRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExistsException("email already registered");
        }
        User user = userMapper.toEntity(dto);
        User save = userRepository.save(user);
        return userMapper.toDto(save);

        // if(userRepository.existByEmail(dto.getEmail())){
        // throw new RuntimeException("user alreadty exist");
        // }
        // User user = new User();
        // user.setName(dto.getName());
        // user.setEmail(dto.getEmail());
        // user.setPhone(dto.getPhone());
        // user.setPassword(dto.getPassword());
        // userRepository.save(user);
        // return new UserResponseDto()

    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("user does not exist"));
        boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!matches) {
            throw new ResourceNotFoundException("password is not matching");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRoles());
        return authMapper.toLoginResponse(user, token);
    }

    @Override
    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return userMapper.toDto(user);
    }
}
