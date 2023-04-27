package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.captcha.CaptchaSettings;
import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.entity.RegistrationBean;
import com.springtraining.furnitureshop.service.AvatarService;
import com.springtraining.furnitureshop.service.CaptchaService;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.AvatarProps;
import com.springtraining.furnitureshop.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;
    private final AvatarService avatarService;
    private final AvatarProps avatarProps;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder, CaptchaService captchaService, AvatarService avatarService, AvatarProps avatarProps) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
        this.avatarService = avatarService;
        this.avatarProps = avatarProps;
    }

    @ModelAttribute(name = "locale")
    public Locale locale(Locale locale) {
        return locale;
    }

    @GetMapping
    protected String displayRegistrationPage(Model model, HttpSession session,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        log.trace("displayRegistrationPage start");
        RegistrationBean bean = (RegistrationBean) session.getAttribute(Attributes.REGISTRATION_BEAN);
        if (bean == null) {
            bean = new RegistrationBean();
        }
        bean.setPageGenerationTime(System.currentTimeMillis());
        log.info("{}: {}", Attributes.REGISTRATION_BEAN, bean);
        model.addAttribute(Attributes.REGISTRATION_BEAN, bean);
        session.removeAttribute(Attributes.REGISTRATION_BEAN);

        BindingResult errors = (BindingResult) session.getAttribute("registration.errors");
        log.info("registration.errors: " + errors);
        model.addAttribute("errors", errors);
        session.removeAttribute("registration.errors");

        String captcha = RandomUtil.generateRandomNumbers(CaptchaSettings.CAPTCHA_SIZE);
        log.info("captcha: " + captcha);
        captchaService.addCaptcha(captcha, request, response);
        String captchaID = (String) request.getAttribute(Attributes.CAPTCHA_ID);
        log.info("captchaID: " + captchaID);
        bean.setCaptchaHiddenField(captchaID);
        model.addAttribute("captcha", captcha);

        model.addAttribute("avatarSize",
                String.format("%dKB", avatarProps.getMaxFileSize().toKilobytes()));

        log.trace("displayRegistrationPage end");
        return "registration";
    }

    @PostMapping
    protected String registerUser(@Valid RegistrationBean registrationBean,
                                  BindingResult errors,
                                  HttpSession session,
                                  HttpServletRequest request) throws IOException {
        log.trace("registerUser start");
        log.info("registrationBean: " + registrationBean);

        if (!errors.hasErrors()) {
            validateRegistrationBean(registrationBean, errors, request);
        }
        log.info("errors: " + errors);

        if (!errors.hasErrors()) {
            User user = registrationBean.toUser(passwordEncoder);
            log.info("user: " + user);
            userService.addUser(user);
            if (registrationBean.getAvatar() != null) {
                log.trace("Save avatar");
                avatarService.save(registrationBean.getAvatar(), user.getAvatar());
            }
            return "redirect:/login";
        } else {
            session.setAttribute("registration.errors", errors);
            session.setAttribute(Attributes.REGISTRATION_BEAN, registrationBean);
            return "redirect:/registration";
        }
    }

    // TODO: 025 25.04.23 create custom spring validator
    private void validateRegistrationBean(RegistrationBean bean,
                                          BindingResult errors,
                                          HttpServletRequest request) {
        if (isPageExpired(bean)) {
            errors.addError(new ObjectError("expiredPage", "registrationPage.message.expiredPage"));
        }
        if (userService.getUserByLogin(bean.getLogin()).isPresent()) {
            errors.addError(new FieldError("registrationBean", "login", "registrationPage.message.existingLogin"));
        }
        if (!validateCaptcha(bean, request)) {
            errors.addError(new FieldError("registrationBean", "captcha", "registrationPage.message.wrongCaptcha"));
        }
        if (!bean.getPassword().equals(bean.getConfirm())) {
            errors.addError(new FieldError("registrationBean", "confirm", "registrationPage.message.differentPasswords"));
        }
    }

    private boolean isPageExpired(RegistrationBean bean) {
        long generationTime = bean.getPageGenerationTime();
        return System.currentTimeMillis() - generationTime > CaptchaSettings.MAX_INTERVAL;
    }

    private boolean validateCaptcha(RegistrationBean bean,
                                    HttpServletRequest request) {
        Optional<String> generatedCaptcha = captchaService.getCaptcha(request);
        return generatedCaptcha.isPresent() && generatedCaptcha.get().equals(bean.getCaptcha());
    }
}
