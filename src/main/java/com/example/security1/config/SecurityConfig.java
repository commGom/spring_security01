package com.example.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity  //시큐리티에 활성화 시켜준다->스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // user/~~ 로 요청이 들어오면 인증이 필요하도록
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('Role_Manager')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()  //다른 요청은 권한이 허용된다.
                .and()
                .formLogin()
                .loginPage("/login");

    }
}
