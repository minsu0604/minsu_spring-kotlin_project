package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    void deleteByProject_ProjectIdAndUser_UserId(int projectId, String userId);

    int countByProject_ProjectId(int projectId);

    List<FavoriteEntity> findAllByUser_UserId(String userId);


    FavoriteEntity findByProject_ProjectIdAndUser_UserId(int projectId, String userId);
}
