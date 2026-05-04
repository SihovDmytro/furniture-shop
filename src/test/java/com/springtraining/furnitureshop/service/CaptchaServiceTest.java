package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.captcha.strategy.CaptchaProviderStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaptchaServiceTest {

    private static final String CAPTCHA_VALUE = "123456";

    @Mock
    private CaptchaProviderStrategy captchaProvider;

    @InjectMocks
    private CaptchaService captchaService;

    @Test
    void addCaptcha_shouldDelegateToStrategy() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        captchaService.addCaptcha(CAPTCHA_VALUE, request, response);

        verify(captchaProvider, times(1)).addCaptcha(CAPTCHA_VALUE, request, response);
    }

    @Test
    void getCaptcha_shouldReturnValueFromStrategy() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(captchaProvider.getCaptcha(request)).thenReturn(Optional.of(CAPTCHA_VALUE));

        Optional<String> result = captchaService.getCaptcha(request);

        assertTrue(result.isPresent());
        assertEquals(CAPTCHA_VALUE, result.get());
        verify(captchaProvider, times(1)).getCaptcha(request);
    }
}

