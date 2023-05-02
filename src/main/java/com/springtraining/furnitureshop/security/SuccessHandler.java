package com.springtraining.furnitureshop.security;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Views;
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

    @Autowired
    public SuccessHandler(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.trace("onAuthenticationSuccess start");
        User user = (User) authentication.getPrincipal();
        log.info(Constants.LOGGER_FORMAT, Attributes.USER, user);
        if (user != null && user.getAttempts() > 0) {
            userService.resetFailedAttempts(user.getLogin());
        }
        super.setDefaultTargetUrl("/" + Views.HOME_PAGE);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
