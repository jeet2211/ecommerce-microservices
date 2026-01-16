package com.ecommerce.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {
    @NotBlank(message = "name required")
    private String name;
    @Email(message = "valid email required")
    private String email;
    @NotBlank(message = "valid password")
    @Size(min = 6,message = "password must be of 6 chars")
    private String password;
    private String phone;
}
