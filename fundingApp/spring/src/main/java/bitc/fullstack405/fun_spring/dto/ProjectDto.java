package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.CategoryEntity;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.util.ImageURL;

import java.time.LocalDateTime;

public record ProjectDto(
        int projectId,
        int goalAmount,
        int currentAmount,
        String title,
        String contents,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int perPrice,
        String thumbnail,
        UserDto user,
        CategoryDto category,
        int numOfSupport,
        int numOfFavorite
) {
    public static ProjectDto of(int goalAmount,
                                int currentAmount,
                                String title,
                                String content,
                                LocalDateTime startDate,
                                LocalDateTime endDate,
                                int perPrice,
                                String thumbnail,
                                UserDto user,
                                CategoryDto category) {

        return new ProjectDto(0, goalAmount, currentAmount, title, ImageURL.projectImg(content), startDate, endDate, perPrice, ImageURL.projectImg2(thumbnail), user, category, 0,0);
    }

    public static ProjectDto of(int id,
                                int goalAmount,
                                int currentAmount,
                                String title,
                                String content,
                                LocalDateTime startDate,
                                LocalDateTime endDate,
                                int perPrice,
                                String thumbnail,
                                UserDto userDto,
                                CategoryDto categoryDto,
                                int numOfSupport,
                                int numOfFavorite) {

        return new ProjectDto(id, goalAmount, currentAmount, title, ImageURL.projectImg(content), startDate, endDate, perPrice, ImageURL.projectImg2(thumbnail), userDto, categoryDto, numOfSupport, numOfFavorite);
    }

    public static ProjectDto of(ProjectEntity entity){
        return new ProjectDto(
                entity.getProjectId(),
                entity.getGoalAmount(),
                entity.getCurrentAmount(),
                entity.getTitle(),
                ImageURL.projectImg(entity.getContents()),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPerPrice(),
                ImageURL.projectImg2(entity.getThumbnail()),
                UserDto.of(entity.getUser()),
                CategoryDto.of(entity.getCategory()),
                entity.getSupportList().size(),
                entity.getFavoriteList().size()
        );
    }

    public ProjectEntity toEntity(UserEntity user, CategoryEntity category){
        return new ProjectEntity(
                projectId,
                goalAmount,
                currentAmount,
                title,
                contents,
                startDate,
                endDate,
                perPrice,
                thumbnail,
                user,
                null,
                null,
                category
        );
    }
}


