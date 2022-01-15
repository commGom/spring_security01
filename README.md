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