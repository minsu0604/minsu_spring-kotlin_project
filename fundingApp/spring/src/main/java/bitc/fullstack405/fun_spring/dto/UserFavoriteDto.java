package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.UserEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public record UserFavoriteDto(
        String userId,
        String userPw,
        String name,
        String email,
        String address,
        List<ProjectDto> favoriteList,
        List<ProjectDto> supportList
) {
    public static UserFavoriteDto of(UserEntity entity){
        return new UserFavoriteDto(
                entity.getUserId(),
                entity.getUserPw(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getFavoriteList()
                        .stream()
                        .map((favorite) ->{
                            return ProjectDto.of(favorite.getProject());
                        })
                        .collect(Collectors.toCollection(LinkedList::new)),
                entity.getSupportList()
                        .stream()
                        .map((support) ->{
                            return ProjectDto.of(support.getProject());
                        })
                        .collect(Collectors.toCollection(LinkedList::new))
        );
    }
}
