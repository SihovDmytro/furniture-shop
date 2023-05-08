package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.entity.LoginBean;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
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

    public static final String ERRORS = "login.errors";

    @ModelAttribute(name = "loginBean")
    public LoginBean loginBean() {
        return new LoginBean();
    }

    @GetMapping
    public String displayLoginPage(Model model, HttpSession session, Locale locale) {
        log.trace("displayLoginPage() start");
        BindingResult errors = (BindingResult) session.getAttribute(ERRORS);
        log.info(Constants.LOGGER_FORMAT, Attributes.ERRORS, errors);
        model.addAttribute(Attributes.ERRORS, errors);
        session.removeAttribute(ERRORS);
        Calendar unbanDate = (Calendar) session.getAttribute(Attributes.UNBAN_DATE);
        log.info(Constants.LOGGER_FORMAT, Attributes.UNBAN_DATE, unbanDate);
        if (unbanDate != null) {
            session.removeAttribute(Attributes.UNBAN_DATE);
            model.addAttribute(Attributes.UNBAN_DATE, unbanDate);
        }
        log.trace("displayLoginPage() end");
        return Views.LOGIN;
    }
}
