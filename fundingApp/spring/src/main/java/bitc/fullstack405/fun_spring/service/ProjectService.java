package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface ProjectService {
    ProjectDto getProjectDetail(int project);

    List<ProjectDto> getProjectList();

    List<ProjectDto> getProjectListRanking();

    List<ProjectDto> getProjectListSearch(String project);

    void getWriteProject(ProjectDto project);


    void updateProject(ProjectDto project);

    List<ProjectDto> getProjectListByDeadLine();

    List<ProjectDto> getHomeScrollProject(int pageNum);

    List<ProjectDto> getProjectListByCategory(int categoryId);

    void updateProject(ProjectEntity project);
}
