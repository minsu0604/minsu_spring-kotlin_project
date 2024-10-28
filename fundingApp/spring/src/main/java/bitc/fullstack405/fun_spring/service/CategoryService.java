package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.CategoryDto;
import bitc.fullstack405.fun_spring.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategory();

    CategoryEntity getCategory(int categoryId);
}
