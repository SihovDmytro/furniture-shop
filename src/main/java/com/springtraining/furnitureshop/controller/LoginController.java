package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.entity.LoginBean;
import com.springtraining.furnitureshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
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
    public String displayLoginPage(Model model, HttpSession session) {
        BindingResult errors = (BindingResult) session.getAttribute("login.errors");
        log.info("displayLoginPage() errors: " + errors);
        model.addAttribute("errors", errors);
        session.removeAttribute("login.errors");
        return "login";
    }

    @PostMapping
    public String login(@Valid LoginBean loginBean, BindingResult errors, HttpSession session) {
        log.info("login() errors: " + errors);
        log.info("login attempt. LoginBean: " + loginBean);
        if (errors.hasErrors()) {
            session.setAttribute("login.errors", errors);
            return "redirect:/login";
        }
        List<String> loginResult = userService.login(loginBean.getLogin(), loginBean.getPassword());
        if (loginResult.isEmpty()) {
            // TODO: 021 21.02.23 add user in session
        } else {
            for (String message : loginResult) {
                errors.addError(new ObjectError("loginError", message));
            }
            session.setAttribute("login.errors", errors);
        }
        return "redirect:/login";
    }

    @PostMapping(path = "/leave")
    public String leave(){
        // TODO: 021 21.02.23 remove user from session
        return "redirect:/login";
    }
}
