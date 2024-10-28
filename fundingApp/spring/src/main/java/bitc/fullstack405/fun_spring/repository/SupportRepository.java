package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.entity.SupportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<SupportEntity, Integer> {

    List<SupportEntity> findAllByUser_UserId(String userId);

    void deleteByProject_ProjectIdAndUser_UserId(int projectId, String userId);

    int countByProject_ProjectId(int projectId);

    SupportEntity findByProject_ProjectIdAndUser_UserId(int projectId, String userId);
}
