package com.springtraining.furnitureshop.captcha.strategy.impl;

import com.springtraining.furnitureshop.captcha.strategy.CaptchaProviderStrategy;
import com.springtraining.furnitureshop.util.Attributes;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class CaptchaProviderSessionStrategyImpl extends CaptchaProviderStrategy {

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Attributes.CAPTCHA, captcha);
    }

    @Override
    public Optional<String> getCaptcha(HttpServletRequest request) {
        Optional<String> foundCaptcha = Optional.ofNullable((String) request.getSession().getAttribute(Attributes.CAPTCHA));
        request.getSession().removeAttribute(Attributes.CAPTCHA);
        log.trace("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }
}
