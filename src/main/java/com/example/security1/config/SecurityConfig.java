package com.example.security1.config;

import com.example.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

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
                .defaultSuccessUrl("/")        //로그인이 성공되면 / url로 보내준다.
                .and()
                .oauth2Login()
                .loginPage("/loginForm")       //후처리가 필요함. (1. 코드를 받고(인증 받아서 구글에 로그인이 됨)
                .userInfoEndpoint()            //->  2. access 토큰을 받고 -> 3. security 서버가 구글 사용자 정보 접근 권한이 생김
                .userService(principalOauth2UserService);             //-> 4. 사용자 정보를 토대로 회원가입 자동으로 진행 or 필요시 추가적인 입력을 받는다
                //구글 로그인이 완료된 뒤의 후처리가 필요한데 codeX(엑세스 토큰+사용자프로필정보를 받는다)
    }
}
