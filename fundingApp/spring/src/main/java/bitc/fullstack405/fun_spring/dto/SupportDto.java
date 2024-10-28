package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;

public record SupportDto(
        int supportId,
        int amount,
        UserDto user,
        ProjectDto project
) {
    public static SupportDto of(SupportEntity entity){
        return new SupportDto(
                entity.getSupportId(),
                entity.getAmount(),
                UserDto.of(entity.getUser()),
                ProjectDto.of(entity.getProject())
        );
    }

    public SupportEntity toEntity(UserEntity user, ProjectEntity project){
        return new SupportEntity(
                supportId,
                amount,
                user,
                project
        );
    }
}
