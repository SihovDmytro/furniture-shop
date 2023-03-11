package com.springtraining.furnitureshop.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginBean {
    @NotNull(message = "Login is empty")
    @Pattern(regexp = "^[\\w_-]{3,20}$", message = "Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.")
    private String login;
    @NotNull(message = "Password is empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Size(max = 64, message = "Too long password")
    private String password;
}
