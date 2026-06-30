package com.orderSystem.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    public WebMvcConfiguration(LoginInterceptor loginInterceptor){
        this.loginInterceptor = loginInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/user/**","/product/**","/order/**")
                .excludePathPatterns("/user/login","/user/register");
    }
}
