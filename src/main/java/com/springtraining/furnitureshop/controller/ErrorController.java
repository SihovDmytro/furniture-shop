package com.springtraining.furnitureshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@Slf4j
@RequestMapping("/errorPage")
public class ErrorController {
    @ModelAttribute(name = "locale")
    public Locale locale(Locale locale) {
        return locale;
    }

    @GetMapping
    public String displayErrorPage(Model model, HttpSession session, HttpServletRequest request) {
        log.trace("displayErrorPage start");
        String title = (String) session.getAttribute("error.title");
        log.info("title: " + title);
        model.addAttribute("title", title == null ? "error.title" : title);
        session.removeAttribute("error.title");

        String referrer = request.getHeader("referer");
        log.info("referrer: " + referrer);
        model.addAttribute("referrer", referrer);
        return "errorPage";
    }
}
