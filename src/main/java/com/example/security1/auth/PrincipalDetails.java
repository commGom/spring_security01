package com.example.security1.auth;

import com.example.security1.model.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티가 /login을 낚아채서 로그인을 진행시킨다
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어 준다. (Security ContextHolder라는 키값에 세션정보를 저장한다)
// 세션에 들어갈 수 있는 오브젝트 => Authentication 타입의 객체
// Authentication 안에 User정보가 있어야 함.
// User오브젝트타입은 UserDetails 타입의 객체이어야한다.
//
//=> 즉 Security 세션 영역에 세션 정보를 저장을 하는데 여기에 저장하는 객체가 Authenticaion 객체이어야하고
// Authencation 객체 안에 User 정보를 저장할 때는 UserDetails 타입의 객체 이어야한다.
public class PrincipalDetails implements UserDetails {
    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    //해당 User의 권한을 리턴하는 곳 user.getRole()은 String 타입이기때문에 GrantedAuthority타입으로 변환 후 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
         collection.add(new GrantedAuthority() {
             @Override
             public String getAuthority() {
                 return user.getRole();
             }
         });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;    //계정 만료 안되었기때문에 true 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
