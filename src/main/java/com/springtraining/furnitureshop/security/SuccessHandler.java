package com.springtraining.furnitureshop.security;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.UserProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final UserProps userProps;

    @Autowired
    public SuccessHandler(UserService userService, UserProps userProps) {
        this.userService = userService;
        this.userProps = userProps;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.trace("onAuthenticationSuccess start");
        User user = (User) authentication.getPrincipal();
        log.info("user: " + user);
        if (user != null && user.getAttempts() > 0) {
            userService.resetFailedAttempts(user.getLogin());
        }
        super.setDefaultTargetUrl("/homePage");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
