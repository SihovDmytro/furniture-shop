package com.springtraining.furnitureshop.security;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class SuccessHandlerTest {
    UserService userService;
    HttpServletRequest request;
    HttpServletResponse response;
    Authentication authentication;

    User user;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authentication = mock(Authentication.class);
        userService = mock(UserService.class);
        user = getUser();
    }

    @Test
    public void shouldResetAttemptsAfterSuccessfulAuthentication() throws ServletException, IOException {
        when(authentication.getPrincipal()).thenReturn(user);

        SuccessHandler successHandler = new SuccessHandler(userService);
        successHandler.onAuthenticationSuccess(request, response, authentication);

        Mockito.verify(userService, times(1)).resetFailedAttempts(user.getLogin());
    }

    public User getUser() {
        return new User("login",
                "name",
                "surname",
                "password",
                "email@email.com",
                false, User.Role.USER,
                2,
                null,
                "");
    }
}