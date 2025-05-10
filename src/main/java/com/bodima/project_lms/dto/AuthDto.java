package com.bodima.project_lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class AuthDto {

    @NotBlank(message = "Email is required!")
    @Pattern(regexp="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message="Invalid email address!")
    @Size(min = 4,max = 255,message = "Email should have 4 to 255 characters")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 4,max = 255,message = "Password should have 4 to 255 characters")
    private String password;
}