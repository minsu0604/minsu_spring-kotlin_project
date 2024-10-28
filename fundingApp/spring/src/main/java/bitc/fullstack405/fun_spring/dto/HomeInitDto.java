package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;

import java.util.List;

public record HomeInitDto(
        List<String> bannerUrl,
        List<CategoryDto> categorys,
        List<ProjectDto> popularProjects,
        List<ProjectDto> deadlineProjects,
        List<ProjectDto> scrollProjects
) {
    public static HomeInitDto of(
            List<String> bannerUrl,
            List<CategoryDto> categorys,
            List<ProjectDto> popularProjects,
            List<ProjectDto> deadlineProjects,
            List<ProjectDto> scrollProjects){

        return new HomeInitDto(bannerUrl, categorys, popularProjects, deadlineProjects, scrollProjects);
    }
}
