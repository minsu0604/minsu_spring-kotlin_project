package bitc.fullstack405.fun_spring.service;

import bitc.fullstack405.fun_spring.dto.CategoryDto;
import bitc.fullstack405.fun_spring.dto.ProjectDto;
import bitc.fullstack405.fun_spring.entity.CategoryEntity;
import bitc.fullstack405.fun_spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryRepository.findAll().stream().map(CategoryDto::of).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public CategoryEntity getCategory(int categoryId) {

        return categoryRepository.findByCategoryId(categoryId);
    }
}
