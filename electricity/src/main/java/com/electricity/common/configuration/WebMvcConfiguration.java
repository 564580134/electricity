package com.electricity.common.configuration;

import com.electricity.common.interceptor.CrossInterceptor;
import com.electricity.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Description: WebMvc过滤器
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    private final CrossInterceptor crossInterceptor;

    private final LoginInterceptor loginInterceptor;

    public WebMvcConfiguration(CrossInterceptor crossInterceptor, LoginInterceptor loginInterceptor) {
        this.crossInterceptor = crossInterceptor;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").
                // 不拦截的路径
                excludePathPatterns(
                        "/v2/api-docs",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/images/**",
                        "/webjars/**",
                        "/ssoLogin/**"
                );
    }
}

