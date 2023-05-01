package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.captcha.CaptchaSettings;
import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.entity.RegistrationBean;
import com.springtraining.furnitureshop.service.AvatarService;
import com.springtraining.furnitureshop.service.CaptchaService;
import com.springtraining.furnitureshop.service.UserService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.AvatarProps;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Parameters;
import com.springtraining.furnitureshop.util.RandomUtil;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/registration")
public class RegistrationController {
    public static final String ERRORS = "registration.errors";
    public static final String AVATAR_SIZE = "%dKB";
    public static final String EXPIRED_PAGE = "registrationPage.message.expiredPage";
    public static final String EXISTING_LOGIN = "registrationPage.message.existingLogin";
    public static final String WRONG_CAPTCHA = "registrationPage.message.wrongCaptcha";
    public static final String DIFFERENT_PASSWORDS = "registrationPage.message.differentPasswords";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;
    private final AvatarService avatarService;
    private final AvatarProps avatarProps;

    @Autowired
    public RegistrationController(UserService userService,
                                  PasswordEncoder passwordEncoder,
                                  CaptchaService captchaService,
                                  AvatarService avatarService,
                                  AvatarProps avatarProps) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.captchaService = captchaService;
        this.avatarService = avatarService;
        this.avatarProps = avatarProps;
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
        log.info(Constants.LOGGER_FORMAT, Attributes.REGISTRATION_BEAN, bean);
        model.addAttribute(Attributes.REGISTRATION_BEAN, bean);
        session.removeAttribute(Attributes.REGISTRATION_BEAN);

        BindingResult errors = (BindingResult) session.getAttribute(ERRORS);
        log.info(Constants.LOGGER_FORMAT, ERRORS, errors);
        model.addAttribute(Attributes.ERRORS, errors);
        session.removeAttribute(ERRORS);

        String captcha = RandomUtil.generateRandomNumbers(CaptchaSettings.CAPTCHA_SIZE);
        log.info(Constants.LOGGER_FORMAT, Attributes.CAPTCHA, captcha);
        captchaService.addCaptcha(captcha, request, response);
        String captchaID = (String) request.getAttribute(Attributes.CAPTCHA_ID);
        log.info(Constants.LOGGER_FORMAT, Attributes.CAPTCHA_ID, captchaID);
        bean.setCaptchaID(captchaID);
        model.addAttribute(Attributes.CAPTCHA, captcha);

        model.addAttribute(Attributes.AVATAR_SIZE,
                String.format(AVATAR_SIZE, avatarProps.getMaxFileSize().toKilobytes()));

        log.trace("displayRegistrationPage end");
        return Views.REGISTRATION;
    }

    @PostMapping
    protected String registerUser(@Valid RegistrationBean registrationBean,
                                  BindingResult errors,
                                  HttpSession session,
                                  HttpServletRequest request) throws IOException {
        log.trace("registerUser start");
        log.info(Constants.LOGGER_FORMAT, Attributes.REGISTRATION_BEAN, registrationBean);

        if (!errors.hasErrors()) {
            validateRegistrationBean(registrationBean, errors, request);
        }
        log.info(Constants.LOGGER_FORMAT, Attributes.ERRORS, errors);

        if (!errors.hasErrors()) {
            User user = registrationBean.toUser(passwordEncoder);
            log.info(Constants.LOGGER_FORMAT, Attributes.USER, user);
            userService.addUser(user);
            if (registrationBean.getAvatar() != null) {
                log.trace("Save avatar");
                avatarService.save(registrationBean.getAvatar(), user.getAvatar());
            }
            return Constants.REDIRECT + Views.LOGIN;
        } else {
            session.setAttribute(ERRORS, errors);
            session.setAttribute(Attributes.REGISTRATION_BEAN, registrationBean);
            return Constants.REDIRECT + Views.REGISTRATION;
        }
    }

    private void validateRegistrationBean(RegistrationBean bean,
                                          BindingResult errors,
                                          HttpServletRequest request) {
        if (isPageExpired(bean)) {
            errors.addError(new ObjectError(Attributes.REGISTRATION_BEAN,
                    EXPIRED_PAGE));
        }
        if (userService.getUserByLogin(bean.getLogin()).isPresent()) {
            errors.addError(new FieldError(Attributes.REGISTRATION_BEAN,
                    Parameters.LOGIN,
                    EXISTING_LOGIN));
        }
        if (!validateCaptcha(bean, request)) {
            errors.addError(new FieldError(Attributes.REGISTRATION_BEAN,
                    Parameters.CAPTCHA,
                    WRONG_CAPTCHA));
        }
        if (!bean.getPassword().equals(bean.getConfirm())) {
            errors.addError(new FieldError(Attributes.REGISTRATION_BEAN,
                    Parameters.CONFIRM,
                    DIFFERENT_PASSWORDS));
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
