package com.example.LogInRedirect;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * created by Atiye Mousavi
 * Date: 10/2/2021
 * Time: 6:50 PM
 */
@Configuration
public class LoginRedirectMvcConfig implements WebMvcConfigurer {
    //برای استفاده از interceptor ما نیاز داریم که ان را در spring اد کنیم

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginPageInterceptor());
    }
}
