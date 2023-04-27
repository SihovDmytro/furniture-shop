package com.springtraining.furnitureshop.captcha.strategy.impl;

import com.springtraining.furnitureshop.captcha.strategy.CaptchaProviderStrategy;
import com.springtraining.furnitureshop.util.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CaptchaProviderCookieStrategyImpl extends CaptchaProviderStrategy {

    @Override
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        String captchaID = addCaptchaToMap(request, captcha);
        Cookie cookie = new Cookie(Attributes.CAPTCHA_ID, captchaID);
        response.addCookie(cookie);
    }

    @Override
    public Optional<String> getCaptcha(HttpServletRequest request) {
        Map<String, String> captchaMap = getCaptchaMap(request);
        log.trace("captchaMap: " + captchaMap);

        Optional<String> captchaID = getCaptchaID(request.getCookies());
        Optional<String> foundCaptcha = getCaptchaFromMap(captchaID, request);
        log.debug("foundCaptcha: " + foundCaptcha);
        return foundCaptcha;
    }

    private static Optional<String> getCaptchaID(Cookie[] cookies) {
        for (Cookie tempCookie : cookies) {
            if (tempCookie.getName().equals(Attributes.CAPTCHA_ID)) {
                return Optional.ofNullable(tempCookie.getValue());
            }
        }
        return Optional.empty();
    }
}
