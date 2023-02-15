package com.springtraining.furnitureshop.captcha.strategy;


import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public abstract class CaptchaProviderStrategy {
    public abstract void addCaptcha(String captcha, HttpServletRequest request, HttpServletResponse response);

    public abstract Optional<String> getCaptcha(HttpServletRequest request);

    protected Map<String, String> getCaptchaMap(HttpServletRequest request) {
        Map<String, String> captchaMap = (Map<String, String>) request.getServletContext().getAttribute(Attributes.CAPTCHA_MAP);
        if (captchaMap == null) {
            captchaMap = new HashMap<>();
            request.getServletContext().setAttribute(Attributes.CAPTCHA_MAP, captchaMap);
        }
        return captchaMap;
    }

    protected String addCaptchaToMap(HttpServletRequest request, String captcha) {
        Map<String, String> captchaMap = getCaptchaMap(request);
        String captchaID = String.valueOf(RandomUtil.generateLong());
        captchaMap.put(captchaID, captcha);
        return captchaID;
    }

    protected Optional<String> getCaptchaFromMap(Optional<String> captchaID, HttpServletRequest request) {
        return captchaID.map((id) -> {
            Map<String, String> captchaMap = getCaptchaMap(request);
            Optional<String> captcha = Optional.ofNullable(captchaMap.get(id));
            captchaMap.remove(id);
            return captcha;
        }).orElse(Optional.empty());
    }
}
