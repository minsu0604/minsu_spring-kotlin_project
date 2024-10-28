package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;


// 생성/삭제를 위한 Dto
public record SupportCD_Dto(
        int projectId,
        String userId
) {
    public SupportEntity toEntity(UserEntity user, ProjectEntity project){
        return new SupportEntity(
                0,
                project.getPerPrice(),
                user,
                project
        );
    }
}
