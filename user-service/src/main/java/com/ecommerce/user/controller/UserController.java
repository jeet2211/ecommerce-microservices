package com.ecommerce.user.controller;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto dto){
        UserResponseDto register = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @GetMapping("/login")
    public  ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto login = userService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(login);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id, Authentication authentication){
        UserResponseDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }


}
