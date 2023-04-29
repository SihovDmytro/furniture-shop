package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.entity.LoginBean;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Locale;

@Controller
@Slf4j
@RequestMapping(path = "/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "loginBean")
    public LoginBean loginBean() {
        return new LoginBean();
    }

    @ModelAttribute(name = "locale")
    public Locale locale(Locale locale) {
        return locale;
    }

    @GetMapping
    public String displayLoginPage(Model model, HttpSession session, Locale locale) {
        log.trace("displayLoginPage() start");
        BindingResult errors = (BindingResult) session.getAttribute("login.errors");
        log.info("errors: " + errors);
        model.addAttribute("errors", errors);
        session.removeAttribute("login.errors");
        Calendar unbanDate = (Calendar) session.getAttribute("unbanDate");
        log.info("unbanDate: " + unbanDate);
        if (unbanDate != null) {
            session.removeAttribute("unbanDate");
            model.addAttribute("unbanDate", DateUtil.dateToString(unbanDate, locale));
        }
        log.trace("displayLoginPage() end");
        return "login";
    }
}
