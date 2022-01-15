package com.example.security1.config.oauth;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest:"+userRequest);
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());
        System.out.println("getAccessToken:"+userRequest.getAccessToken());

        OAuth2User oAuth2User=super.loadUser(userRequest);
        System.out.println("super.loadUser(userRequest).getAttributes():"+super.loadUser(userRequest).getAttributes());
        System.out.println("oAuth2User.getAttributes():"+oAuth2User.getAttributes());

        String provider=userRequest.getClientRegistration().getClientName(); //google
        String providerId=oAuth2User.getAttribute("sub");
        String username=provider+"_"+providerId;
        //그냥 만들어주는 password Oauth로 가입시에 비밀번호는 딱히 필요없다.
        String password=bCryptPasswordEncoder.encode("commGom");
        String email=oAuth2User.getAttribute("email");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if (userEntity==null){
            //회원가입 가능(중복아이디 없기 때문에)
            userEntity=User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else{

        }
        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
