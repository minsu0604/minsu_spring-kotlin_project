package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.UserDto;
import bitc.fullstack405.fun_spring.dto.UserFavoriteDto;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findUserByUserIdAndPw(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw);
    }

    @Override
    public void saveUser(UserDto user) {
        userRepository.save(user.toEntity());
    }

    @Override
    public Boolean isUserInfo(String userId, String userPw) {
        // 사용자가 있을 경우 true 를 반환하는 로직
        return userRepository.findByUserIdAndUserPw(userId, userPw) != null;
    }

    @Override
    public UserEntity findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public UserFavoriteDto getUserFavorite(String userId) {
        UserEntity user = userRepository.findByUserId(userId);

        return UserFavoriteDto.of(user);
    }


}

