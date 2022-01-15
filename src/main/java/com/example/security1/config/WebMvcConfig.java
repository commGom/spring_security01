package com.example.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //View Resolver 환경설정하는 클래스
public class WebMvcConfig implements WebMvcConfigurer {
    //패스워드 암호화를 위하여 사용한다. Bean 어노테이션을 사용하면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록된다.
    @Bean
    public BCryptPasswordEncoder encodedPwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setCharset("UTF-8");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");

        registry.viewResolver(resolver);
    }
}
