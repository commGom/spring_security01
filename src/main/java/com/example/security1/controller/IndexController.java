package com.example.security1.controller;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 return
public class IndexController {


    //머스테치 기본폴더 src/main/resources로 잡힌다
    //view resolver 설정할 때, prefix : templates, suffix : .mustache
    //머스테치 의존성 라이브러리 잡으면 자동으로  설정된다.
    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    //OAuth 로그인, 일반 로그인 하였을 때, PrincipalDetails로 받을 수 있다.
    //@AuthenticationPrincipal 어노테이션
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails"+principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinFrom(){
        return "joinForm";
    }
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");
        String rawPassword=user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        System.out.println(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    //데이터라는 메서드가 실행되기 직전에 실행된다. 여러개의 권한 설정 해주고 싶을 때
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("/test/login========>authentication:"+authentication.getPrincipal());
        PrincipalDetails principalDetails=(PrincipalDetails) authentication.getPrincipal();
        System.out.println("Authentication : "+principalDetails.getUser());

        System.out.println("userDetails:"+userDetails.getUser());
        return "test Login";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String oauthLoginTest(Authentication authentication,
    @AuthenticationPrincipal OAuth2User oauth){
        System.out.println("/test/login========>authentication:"+authentication.getPrincipal());
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User.getAttributes(): "+oAuth2User.getAttributes());
        System.out.println(" @AuthenticationPrincipal OAuth2User 의 getAttributes()"+oauth.getAttributes());
        return "Oauth session 정보 확인";
    }
}
