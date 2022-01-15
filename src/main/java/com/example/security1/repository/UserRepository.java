package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//JpaRepository가 CRUD 메서드를 가지고 있다.
//@Repository라는 어노테이션이 없어도 IoC가 된다. JpaRepository를 상속했기 때문에 자동으로 빈이 생성되어서
//필요한 곳에서 @Autowired로 의존성 주입을 해주면된다.
public interface UserRepository extends JpaRepository<User,Long> {
}
