package bitc.fullstack405.fun_spring.dto;


import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;

// 생성 / 삭제를 위한 Dto
public record FavoriteCD_Dto(
        int projectId,
        String userId
) {
    public FavoriteEntity toEntity(UserEntity user, ProjectEntity project){
        return new FavoriteEntity(
                0,
                user,
                project
                );
    }
}
