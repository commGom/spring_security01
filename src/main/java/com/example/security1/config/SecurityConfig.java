package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  //시큐리티에 활성화 시켜준다->스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
//secured 어노테이션 활성화시켜주는 어노테이션, prePostAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //패스워드 암호화를 위하여 사용한다. Bean 어노테이션을 사용하면 해당 메서드의 리턴되는 오브젝트를 IoC로 등록된다.
    @Bean
    public BCryptPasswordEncoder encodedPwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //csrf 비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // user/~~ 로 요청이 들어오면 인증이 필요하도록
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()  //다른 요청은 권한이 허용된다.
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")    //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행한다. // (Controller에 login url안 만들어도된다.)
                .defaultSuccessUrl("/");        //로그인이 성공되면 / url로 보내준다.
    }
}
