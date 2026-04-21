package com.springtraining.furnitureshop.service;


import com.springtraining.furnitureshop.captcha.strategy.CaptchaProviderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class CaptchaService {
    private final CaptchaProviderStrategy captchaProvider;

    @Autowired
    public CaptchaService(CaptchaProviderStrategy captchaProvider) {
        this.captchaProvider = captchaProvider;
    }

    /**
     * Adds a captcha value to the current request/response via the configured strategy.
     *
     * @param captcha  the captcha value to store
     * @param request  the current HTTP request; must not be {@code null}
     * @param response the current HTTP response; must not be {@code null}
     */
    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        captchaProvider.addCaptcha(captcha, request, response);
    }

    /**
     * Retrieves the captcha value associated with the current request via the configured strategy.
     *
     * @param request the current HTTP request; must not be {@code null}
     * @return an {@link Optional} containing the captcha value, or empty if none is found
     */
    public Optional<String> getCaptcha(HttpServletRequest request) {
        return captchaProvider.getCaptcha(request);
    }
}
