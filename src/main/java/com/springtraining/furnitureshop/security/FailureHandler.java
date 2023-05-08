package com.springtraining.furnitureshop.security;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.LocalizationTags;
import com.springtraining.furnitureshop.util.Parameters;
import com.springtraining.furnitureshop.util.UserProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.springtraining.furnitureshop.util.LocalizationTags.BAD_CREDENTIALS;

@Component
@Slf4j
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final String FAILURE_URL = "/login?error=true";
    private final UserService userService;
    private final UserProps userProps;

    @Autowired
    public FailureHandler(UserService userService, UserProps userProps) {
        this.userService = userService;
        this.userProps = userProps;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.trace("onAuthenticationFailure start");
        String login = request.getParameter(Parameters.LOGIN);
        log.info(Constants.LOGGER_FORMAT, Parameters.LOGIN, login);
        String message = BAD_CREDENTIALS;
        if (userService.getUserByLogin(login).isPresent()) {
            User user = userService.getUserByLogin(login).get();
            log.info(Constants.LOGGER_FORMAT, Attributes.USER, user);
            if (user.isAccountNonLocked() && user.isEnabled()) {
                log.trace("account is active");
                if (user.getAttempts() < userProps.getMaxLoginAttempts()) {
                    log.trace("increase failed attempts");
                    userService.increaseFailedAttempts(user);
                } else {
                    message = LocalizationTags.LOCKED_ACCOUNT;
                    log.trace("lock account: " + login, exception);
                    userService.ban(user);
                    userService.resetFailedAttempts(user.getLogin());
                }
            } else {
                log.trace("account is locked");
                message = LocalizationTags.LOCKED_ACCOUNT;
            }
            if (message.equals(LocalizationTags.LOCKED_ACCOUNT)) {
                request.getSession().setAttribute(Attributes.UNBAN_DATE, user.getUnban());
            }
        }
        exception = new LockedException(message);
        super.setDefaultFailureUrl(FAILURE_URL);
        super.onAuthenticationFailure(request, response, exception);
    }
}
