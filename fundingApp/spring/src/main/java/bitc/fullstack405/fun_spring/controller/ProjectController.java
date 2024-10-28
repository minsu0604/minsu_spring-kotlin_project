package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    // 상세보기
    @GetMapping("/{projectId}")
    public ProjectDto getProjectDetail(@PathVariable(name = "projectId") int projectId) {
        ProjectDto project;
        project = projectService.getProjectDetail(projectId);

        return project;
    }

    // 리스트
    @GetMapping("/list")
    public Object getProjectList() {
        List<ProjectDto> list;
        list = projectService.getProjectList();

        return list;
    }

    // 프로젝트 인기순
    @GetMapping("/list/ranking")
    public Object getProjectRankingList() {
        List<ProjectDto> list;
        list = projectService.getProjectListRanking();

        return list;
    }

    // 검색 Key로 시작하는 title을 가진 프로젝트 리스트
    @GetMapping("/search/{searchKey}")
    public Object getSearchSuggestList(@PathVariable String searchKey) {
        List<ProjectDto> list;
        list = projectService.getProjectListSearch(searchKey);
        List<String> searchResult = list.stream().map(ProjectDto::title).collect(Collectors.toCollection(LinkedList::new));
        return searchResult;
    }

    @GetMapping("/search/result/{searchKey}")
    public Object getProjectsBySearchKey(@PathVariable String searchKey){
        return projectService.getProjectListSearch(searchKey);
    }

    // 프로젝트 작성
    @PostMapping("/write")
    public void writeProject(
            @RequestBody ProjectDto projectDto) {

        projectService.getWriteProject(projectDto);
    }

    @GetMapping("/deadline")
    public List<ProjectDto> deadlineProject(){
        var list = projectService.getProjectListByDeadLine();
        return list;
    }

    @GetMapping("/category/{categoryId}")
    public List<ProjectDto> getProjectByCategory(@PathVariable int categoryId){
        return projectService.getProjectListByCategory(categoryId);
    }

}
