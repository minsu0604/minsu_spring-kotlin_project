package bitc.fullstack405.fun_spring.controller;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.dto.SupportCD_Dto;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.service.ProjectService;
import bitc.fullstack405.fun_spring.service.SupportService;
import bitc.fullstack405.fun_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
public class SupportController {

    @Autowired
    private SupportService supportService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    // 후원한 유저 수
    @GetMapping("/support/count/{projectId}")
    public Object getSupportUserCount(@PathVariable int projectId) {
        return supportService.getSupportUserCount(projectId);
    }


    // 자신이 후원한 프로젝트 리스트
    @GetMapping("/support/project/{userId}")
    public List<ProjectDto> getSupportingProject(@PathVariable String userId) {
        return supportService.getSupportingListByUserId(userId);
    }

    @PostMapping("/support/check")
    public Boolean checkSupporting(@RequestBody SupportCD_Dto supportCDDto){
        return supportService.checkSupporting(supportCDDto);
    }

    // 후원하기
    @PostMapping("/support")
    public void CreateSupport(@RequestBody SupportCD_Dto supportCDDto) {

        supportService.createSupport(supportCDDto);

    }

    // 후원 취소
    @DeleteMapping("/support/delete")
    public void supportCancel(@RequestBody SupportCD_Dto support) {

        supportService.supportCancel(support);
    }

}
