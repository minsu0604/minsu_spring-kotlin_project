package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {


    //find
    //save
    //delete

    ProjectEntity findByProjectId(int projectId);

    List<ProjectEntity> findTop10ByTitleContaining(String key);


    List<ProjectEntity> findTop8ByOrderByStartDate();
    //
//    List<ProjectEntity> findTop13OrderBy();

//    List<ProjectEntity> findTop13();


    List<ProjectEntity> findAllByCategory_CategoryId(int categoryId);

    List<ProjectEntity> findTop6By();


    @Query("select p from ProjectEntity as p where p.category.categoryId = :category order by case when p.contents like '%png' then 0 else 1 end")
    List<ProjectEntity> findAllByContentsKeyCategory(int category);

    @Query("select p from ProjectEntity as p order by case when p.contents like '%png' then 0 else 1 end")
    Page<ProjectEntity> findTopNOrderByContentsKey(Pageable pageable);


    // 마감 임박순으로 size 만큼
    @Query(value = "select p from ProjectEntity as p order by case when p.contents like '%png' then 0 else 1 end , p.endDate - now() DESC")
    Page<ProjectEntity> findTop9By(Pageable pageable);


    @Query("select p from ProjectEntity as p where p.title like concat('%', :key, '%') order by case when p.contents like '%png' then 0 else 1 end")
    List<ProjectEntity> querySearchTitle(String key);

    @Query("select p from ProjectEntity as p where p.projectId >= 101 and p.category.categoryId = 5 order by p.projectId")
    List<ProjectEntity> getPurfumeList();
}
