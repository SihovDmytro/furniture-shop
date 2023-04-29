package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@Slf4j
@RequestMapping("/homePage")
public class HomePageController {

    @ModelAttribute(name = "locale")
    public Locale locale(Locale locale) {
        return locale;
    }

    @ModelAttribute("user")
    public User user(Authentication authentication) {
        User user = null;
        if (authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }
        log.trace("user: " + user);
        return user;
    }

    @GetMapping
    public String displayHomePage() {
        log.trace("displayHomePage start");
        return "homePage";
    }
}
