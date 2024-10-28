package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.dto.SupportCD_Dto;
import bitc.fullstack405.fun_spring.entity.SupportEntity;

import java.util.List;

public interface SupportService {

    int getSupportUserCount(int projectId);

    List<ProjectDto> getSupportingListByUserId(String userId);

    void supportCancel(SupportCD_Dto supportCDDto);

    void createSupport(SupportCD_Dto supportCDDto);

    Boolean checkSupporting(SupportCD_Dto supportCDDto);
}
