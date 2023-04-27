package com.springtraining.furnitureshop.controller;

import com.github.cage.GCage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/captcha-generator")
public class CaptchaController {

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] generateCaptchaImage(@RequestParam String text) {
        log.trace("generateCaptchaImage start");
        GCage gCage = new GCage();
        log.info("text: " + text);
        return gCage.draw(text);
    }
}
