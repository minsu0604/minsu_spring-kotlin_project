package bitc.fullstack405.fun_spring.repository;

import bitc.fullstack405.fun_spring.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity findByCategoryId(int categoryId);
}
