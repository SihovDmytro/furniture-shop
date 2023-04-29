package com.springtraining.furnitureshop.config;

import com.springtraining.furnitureshop.captcha.strategy.CaptchaProviderStrategy;
import com.springtraining.furnitureshop.captcha.strategy.impl.CaptchaProviderHiddenFieldStrategyImpl;
import com.springtraining.furnitureshop.domain.User;
import com.springtraining.furnitureshop.repository.UserRepository;
import com.springtraining.furnitureshop.security.FailureHandler;
import com.springtraining.furnitureshop.security.SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
public class SecurityConfig {
    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;

    public SecurityConfig(SuccessHandler successHandler, FailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    public CaptchaProviderStrategy captchaProviderStrategy() {
        return new CaptchaProviderHiddenFieldStrategyImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return (username -> userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found")));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/cart", "/homePage").hasRole(User.Role.USER.toString())
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()
                .headers(headers -> headers.referrerPolicy(
                        (referer) -> referer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN)))
                .build();
    }
}
