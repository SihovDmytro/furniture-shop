package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/homePage")
public class HomePageController {

    @ModelAttribute("user")
    public User user(Authentication authentication) {
        User user = null;
        if (authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }
        log.info(Constants.LOGGER_FORMAT, Attributes.USER, user);
        return user;
    }

    @GetMapping
    public String displayHomePage() {
        log.trace("displayHomePage start");
        return Views.HOME_PAGE;
    }
}
