package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.dto.SupportCD_Dto;
import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import bitc.fullstack405.fun_spring.entity.SupportEntity;
import bitc.fullstack405.fun_spring.entity.UserEntity;
import bitc.fullstack405.fun_spring.repository.ProjectRepository;
import bitc.fullstack405.fun_spring.repository.SupportRepository;
import bitc.fullstack405.fun_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportServiceImpl implements SupportService {

    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public int getSupportUserCount(int projectId) {

        return supportRepository.countByProject_ProjectId(projectId);
    }

    @Override
    public List<ProjectDto> getSupportingListByUserId(String userId) {

        return supportRepository.findAllByUser_UserId(userId)
                .stream()
                .map(supportEntity -> {
                    return ProjectDto.of(supportEntity.getProject());
                }).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public void createSupport(SupportCD_Dto supportCDDto) {
        if(supportRepository.findByProject_ProjectIdAndUser_UserId(
                supportCDDto.projectId(),
                supportCDDto.userId()
        ) != null){
            return;
        }

        ProjectEntity project = projectRepository.findByProjectId(supportCDDto.projectId());
        UserEntity user = userRepository.findByUserId(supportCDDto.userId());

        project.setCurrentAmount(project.getCurrentAmount() + project.getPerPrice());
        projectService.updateProject(project);

        supportRepository.save(supportCDDto.toEntity(user, project));
    }

    @Override
    public Boolean checkSupporting(SupportCD_Dto supportCDDto) {
        return supportRepository.findByProject_ProjectIdAndUser_UserId(supportCDDto.projectId(), supportCDDto.userId()) != null;
    }

    @Override
    public void supportCancel(SupportCD_Dto supportCDDto) {
        ProjectEntity project = projectRepository.findByProjectId(supportCDDto.projectId());

        project.setCurrentAmount(project.getCurrentAmount() - project.getPerPrice());
        projectService.updateProject(project);

        supportRepository.deleteByProject_ProjectIdAndUser_UserId(supportCDDto.projectId(), supportCDDto.userId());
    }


}
