package com.springtraining.furnitureshop.captcha;

import java.util.concurrent.TimeUnit;

public class CaptchaSettings {
    public static final int CAPTCHA_SIZE = 6;
    public static final long MAX_INTERVAL = TimeUnit.MINUTES.toMillis(1);
}
