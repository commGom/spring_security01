package com.example.security1.config.auth;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서.loginProcessingUrl("login")
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    //시큐리티 세션은 Authentication 타입 객체 필요, Authentication의 유저 정보는 UserDetails 타입이 필요
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User userEntity = userRepository.findByUsername(username);
        if (userEntity!=null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
