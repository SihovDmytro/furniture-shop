package com.springtraining.furnitureshop.config;

import com.springtraining.furnitureshop.util.LocalizationProps;
import com.springtraining.furnitureshop.util.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.math.BigInteger;
import java.util.Locale;

@Configuration
public class LocalizationConfig implements WebMvcConfigurer {
    private final LocalizationProps props;

    @Autowired
    public LocalizationConfig(LocalizationProps props) {
        this.props = props;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        localeResolver.setCookieMaxAge(props.getCookieMaxAge());
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(Parameters.LANGUAGE);
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
