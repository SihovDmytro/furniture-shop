package com.springtraining.furnitureshop.captcha.strategy.impl;


import com.springtraining.furnitureshop.captcha.strategy.CaptchaProviderStrategy;
import com.springtraining.furnitureshop.util.Attributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CaptchaProviderSessionStrategyImplTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CaptchaProviderStrategy captchaProvider = new CaptchaProviderSessionStrategyImpl();

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void shouldPutCaptchaInSession() {
        when(request.getSession()).thenReturn(session);
        String captcha = "1234560";

        captchaProvider.addCaptcha(captcha, request, response);

        verify(session, times(1)).setAttribute(Attributes.CAPTCHA, captcha);
    }

    @Test
    public void shouldReturnCaptchaValue() {
        when(request.getSession()).thenReturn(session);
        String captchaValue = "1234560";
        when(session.getAttribute(Attributes.CAPTCHA)).thenReturn(captchaValue);

        Assertions.assertEquals(captchaValue, captchaProvider.getCaptcha(request).get());
    }
}