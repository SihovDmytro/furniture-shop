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

    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_NAME = "testName";
    private static final String TEST_SURNAME = "testSurname";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String TEST_EMAIL = "test@email.com";
    private static final int MAX_ATTEMPTS = 3;
    private static final int BAN_DURATION = 600;

    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private AuthenticationException exception;
    private final UserProps userProps = new UserProps(MAX_ATTEMPTS, BAN_DURATION);

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        exception = mock(AuthenticationException.class);
        userService = mock(UserService.class);
        session = mock(HttpSession.class);
    }

    @Test
    void shouldIncreaseFailedAttempts() throws ServletException, IOException {
        User user = buildUser(0);

        when(request.getParameter(Parameters.LOGIN)).thenReturn(user.getLogin());
        when(userService.getUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        FailureHandler failureHandler = new FailureHandler(userService, userProps);
        failureHandler.onAuthenticationFailure(request, response, exception);

        Mockito.verify(userService, times(1)).increaseFailedAttempts(user);
    }

    @Test
    void shouldBanUserWhenAttemptsLimitIsReached() throws ServletException, IOException {
        User user = buildUser(userProps.getMaxLoginAttempts());

        when(request.getParameter(Parameters.LOGIN)).thenReturn(user.getLogin());
        when(userService.getUserByLogin(user.getLogin())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        FailureHandler failureHandler = new FailureHandler(userService, userProps);
        failureHandler.onAuthenticationFailure(request, response, exception);

        Mockito.verify(userService, times(1)).ban(user);
        Mockito.verify(userService, times(1)).resetFailedAttempts(user.getLogin());
    }

    private User buildUser(int attempts) {
        return new User(TEST_LOGIN,
                TEST_NAME,
                TEST_SURNAME,
                TEST_PASSWORD,
                TEST_EMAIL,
                false, User.Role.USER,
                attempts,
                null,
                "");
    }
}