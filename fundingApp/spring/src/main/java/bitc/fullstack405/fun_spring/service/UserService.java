package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.UserDto;
import bitc.fullstack405.fun_spring.dto.UserFavoriteDto;
import bitc.fullstack405.fun_spring.entity.UserEntity;

public interface UserService {
    UserEntity findUserByUserIdAndPw(String userId, String userPw);

    void saveUser(UserDto user);

    Boolean isUserInfo(String userId, String userPw);

    UserEntity findByUserId(String userId);

    UserFavoriteDto getUserFavorite(String userId);
}
