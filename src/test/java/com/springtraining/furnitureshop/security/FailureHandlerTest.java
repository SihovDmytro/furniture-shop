package com.springtraining.furnitureshop.security;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Parameters;
import com.springtraining.furnitureshop.util.UserProps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class FailureHandlerTest {
    UserService userService;
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    AuthenticationException exception;
    UserProps userProps = new UserProps(3, 600);


    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        exception = mock(AuthenticationException.class);
        userService = mock(UserService.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void shouldIncreaseFailedAttempts() throws ServletException, IOException {
        User user = getUser();

        when(request.getParameter(Parameters.LOGIN)).thenReturn(user.getLogin());
        when(userService.getUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        FailureHandler failureHandler = new FailureHandler(userService, userProps);
        failureHandler.onAuthenticationFailure(request, response, exception);

        Mockito.verify(userService, times(1)).increaseFailedAttempts(user);
    }

    @Test
    public void shouldBanUserWhenAttemptsLimitIsReached() throws ServletException, IOException {
        User user = getUser();
        user.setAttempts(userProps.getMaxLoginAttempts());

        when(request.getParameter(Parameters.LOGIN)).thenReturn(user.getLogin());
        when(userService.getUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        FailureHandler failureHandler = new FailureHandler(userService, userProps);
        failureHandler.onAuthenticationFailure(request, response, exception);

        Mockito.verify(userService, times(1)).ban(user);
        Mockito.verify(userService, times(1)).resetFailedAttempts(user.getLogin());
    }


    public User getUser() {
        return new User("login",
                "name",
                "surname",
                "password",
                "email@email.com",
                false, User.Role.USER,
                0,
                null,
                "");
    }
}