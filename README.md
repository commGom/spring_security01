# spring_security01

## 1. 프로젝트 생성
### 1-1. 라이브러리 선택
- DevTools
- Lombok
- Spring Data JPA
- MySQL Driver
- Spring Security
- Mustache
- Spring Web

## 2. SecurityConfig 환경설정

## 3. 로그인 페이지, 회원가입 페이지 만들고 DB에 저장
- BcryptPasswordEncoder를 이용하여 암호화된 비밀번호 저장

## 4. 암호화 된 비밀번호로 로그인 하기

## 5. 시큐리티 권한 처리
### 5-1. ROLE=> ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
### 5-2. 특정 메서드에 시큐리티 권한 설정이 가능 @EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
    - ex)Controller 메서드 위에 @Secured("ROLE_ADMIN")
    - @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

# spring_security02(OAuth2.0 SNS 로그인)

## 1. Google
### 1-1. pom.xml에 spring-boot-starter-oauth2-client 라이브러리 주입
### 1-2. application.yml 에 설정 client-id, client-secret, scope 작성
### 1-3. 구글 로그인 버튼 만들고 요청 URI 지정
- /oauth2/authorization/google
### 1-4. SecurityConfig에서 OAuth2 로그인 관련 설정
### 1-5. 구글 로그인 완료된 뒤 후처리
- 1. 코드를 받고(인증 받아서 구글에 로그인이 됨)
- 2. access 토큰을 받고
- 3. security 서버가 구글 사용자 정보 접근 권한이 생김
- OAuth2.0-client 라이브러리를 이용하면 access 토큰과 사용자 프로필 정보를 받는다
- 4. 사용자 정보를 토대로 회원가입 자동으로 진행 or 필요시 추가적인 입력을 받는다.
### 1-6. DefaultOAuth2UserService를 상속받는 OAuth2UserService 클래스 생성   
- SecurityConfig 설정 파일에서 Autowired해서 주입하여 사용
### 1-7. 상속받은 클래스에서 loadUser를 재정의하여 후처리 진행
- 구글로 부터 받은 userRequest 데이터에 대한 후처리
- super.loadUser(userRequest).getAttributes()에서 사용자 정보를 받아오기 때문에 이것 사용
### 1-8. User DB에 저장할 추가 데이터 (provider, providerId)
### 1-9. 일반 로그인, SNS 로그인 처리 (Authentication을 이용하여.getPrincipal())
- 일반 로그인 principalDetails (UserDetails 상속받은 빈 이용) @AuthenticationPrincipal PrincipalDetails
- SNS 로그인 OAuth2User 객체 이용 @AuthenticationPrincipal OAuth2User
### 1-10. PrincipalDetails이 UserDetails, OAuth2User를 상속받아서 처리
- loadUser() 가 종료될 때 AuthenticationPrincipal이 생성된다.
