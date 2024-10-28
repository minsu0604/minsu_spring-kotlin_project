package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.UserEntity;

public record UserDto(
        String userId,
        String userPw,
        String name,
        String email,
        String address
){
    public static UserDto of(UserEntity entity) {
        return new UserDto(
                entity.getUserId(),
                entity.getUserPw(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress()
        );
    }


    public UserEntity toEntity(){
        return new UserEntity(
                userId,
                userPw,
                name,
                email,
                address,
                null,
                null,
                null
        );
    }
}
