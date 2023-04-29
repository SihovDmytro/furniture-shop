package com.springtraining.furnitureshop.security;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.DateUtil;
import com.springtraining.furnitureshop.util.UserProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
@Slf4j
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final String LOCKED_ACCOUNT = "loginPage.message.userIsBanned";
    public static final String BAD_CREDENTIALS = "loginPage.message.wrongCredentials";
    private final UserService userService;
    private final UserProps userProps;

    @Autowired
    public FailureHandler(UserService userService, UserProps userProps) {
        this.userService = userService;
        this.userProps = userProps;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.trace("onAuthenticationFailure start");
        String login = request.getParameter("login");
        log.info("login: " + login);
        String message = BAD_CREDENTIALS;
        if (userService.getUserByLogin(login).isPresent()) {
            User user = userService.getUserByLogin(login).get();
            log.info("user: " + user);
            if (user.isAccountNonLocked() && user.isEnabled()) {
                log.trace("account is active");
                if (user.getAttempts() < userProps.getMaxLoginAttempts()) {
                    log.trace("increase failed attempts");
                    userService.increaseFailedAttempts(user);
                } else {
                    message = LOCKED_ACCOUNT;
                    log.info("lock account: " + login, exception);
                    userService.ban(user);
                    userService.resetFailedAttempts(user.getLogin());
                }
            } else {
                log.trace("account is locked");
                message = LOCKED_ACCOUNT;
            }
            if (message.equals(LOCKED_ACCOUNT)){
                request.getSession().setAttribute("unbanDate", user.getUnban());
            }
        }
        exception = new LockedException(message);
        super.setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}
