package com.nit.schoolorder.config;

import com.nit.schoolorder.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: CCTY
 * @Date: 2023/3/6 20:39
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**/")// 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/admin/login", "/admin/register","/*/export","/*/import","/file/**");   //这些接口放行不用验证
    }

//        要通过Bean的方式把JwtInterceptor注入到容器中
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
