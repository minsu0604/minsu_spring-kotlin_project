package bitc.fullstack405.fun_spring.repository;


import bitc.fullstack405.fun_spring.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserIdAndUserPw(String userId, String userPw);

    UserEntity findByUserId(String userId);
}
