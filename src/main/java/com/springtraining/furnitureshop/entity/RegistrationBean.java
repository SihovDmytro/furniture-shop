package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrationBean {
    @NotNull(message = "Login is empty")
    @Pattern(regexp = "^[\\w_-]{3,20}$", message = "Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.")
    private String login;

    @NotNull(message = "Name is empty")
    @Size(max = 30, message = "Name is too long")
    @Size(min = 1, message = "Name is too short")
    private String name;

    @NotNull(message = "Surname is empty")
    @Size(max = 30, message = "Surname is too long")
    @Size(min = 1, message = "Surname is too short")
    private String surname;

    @NotNull(message = "Password is empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Size(max = 64, message = "Too long password")
    private String password;

    @NotNull(message = "Password is empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Size(max = 64, message = "Too long password")
    private String confirm;

    @Email(message = "Isn't email")
    @Size(max = 30, message = "Too long email")
    @Size(min = 5, message = "Email is too short")
    private String email;

    private boolean sendMail;

    @NotNull(message = "Captcha is empty")
    private String captcha;

    private MultipartFile avatar;

    private String captchaID;

    private long pageGenerationTime;


    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(getLogin(),
                getName(),
                getSurname(),
                passwordEncoder.encode(getPassword()),
                getEmail(),
                isSendMail(),
                User.Role.USER,
                0,
                null,
                String.format("%s.png", getLogin()));
    }
}
