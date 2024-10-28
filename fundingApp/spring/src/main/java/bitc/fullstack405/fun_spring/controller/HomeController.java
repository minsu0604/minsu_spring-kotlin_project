package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.HomeInitDto;
import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.dto.UserFavoriteDto;
import bitc.fullstack405.fun_spring.service.CategoryService;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.UserService;
import bitc.fullstack405.fun_spring.util.ImageURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageURL imageURL;

    @GetMapping({"", "/"})
    public HomeInitDto getHomeInitData(){

        var homeInfo = HomeInitDto.of(
                imageURL.bannerImages(),
                categoryService.getAllCategory(),
                projectService.getProjectListRanking(),
                projectService.getProjectListByDeadLine(),
                projectService.getHomeScrollProject(0));

        return homeInfo;
    }

    @GetMapping({"/homeScroll/{pageNum}"})
    public List<ProjectDto> getHomeScrollProject(@PathVariable int pageNum){
        return projectService.getHomeScrollProject(pageNum);
    }

    @GetMapping("/page/favorite/{userId}")
    public UserFavoriteDto getUserFavorite(@PathVariable String userId){
        return userService.getUserFavorite(userId);
    }
}
