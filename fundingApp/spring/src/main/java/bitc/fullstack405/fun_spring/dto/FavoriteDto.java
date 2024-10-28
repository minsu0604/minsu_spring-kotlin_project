package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;

public record FavoriteDto(
        int favoriteId,
        UserDto user,
        ProjectDto project
) {
    public static FavoriteDto of(FavoriteEntity entity){
        return new FavoriteDto(
                entity.getFavoriteId(),
                UserDto.of(entity.getUser()),
                ProjectDto.of(entity.getProject())
        );
    }

    public FavoriteEntity toEntity(UserEntity user, ProjectEntity project){
        return new FavoriteEntity(
                favoriteId,
                user,
                project
        );
    }
}
