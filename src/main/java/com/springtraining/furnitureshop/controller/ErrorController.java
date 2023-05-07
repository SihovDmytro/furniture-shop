package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.LocalizationTags;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/errorPage")
public class ErrorController {

    @GetMapping
    public String displayErrorPage(Model model, HttpSession session, HttpServletRequest request) {
        log.trace("displayErrorPage start");
        String title = (String) session.getAttribute(LocalizationTags.ERROR_TITLE);
        log.info(Constants.LOGGER_FORMAT, LocalizationTags.ERROR_TITLE, title);
        model.addAttribute(Attributes.TITLE, title == null ? LocalizationTags.ERROR_TITLE : title);
        session.removeAttribute(LocalizationTags.ERROR_TITLE);

        String referrer = request.getHeader(Attributes.REFERRER);
        log.info(Constants.LOGGER_FORMAT, Attributes.REFERRER, referrer);
        model.addAttribute(Attributes.REFERRER, referrer);
        return Views.ERROR_PAGE;
    }
}
