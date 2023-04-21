package com.chaminju.firstproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // spring의 설정 바꿀 수 있도록 하는 것 (implemets 해서 어떤 설정을 바꿀 것인지 적기)
public class CorsConfig implements WebMvcConfigurer {
    
    public void addCorsMappings(CorsRegistry registry) {
        registry
        .addMapping("/**") // 모든 path에 대해서 허용
        .allowedMethods("*") //모든 method에 대해서 허용
        .allowedOrigins("*"); // 모든 출처에 대해서 허용
    }
}
