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


    public void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response) {
        captchaProvider.addCaptcha(captcha, request, response);
    }


    public Optional<String> getCaptcha(HttpServletRequest request) {
        return captchaProvider.getCaptcha(request);
    }
}
