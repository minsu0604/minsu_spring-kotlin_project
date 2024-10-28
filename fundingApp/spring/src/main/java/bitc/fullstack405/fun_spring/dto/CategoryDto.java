package bitc.fullstack405.fun_spring.dto;

import bitc.fullstack405.fun_spring.entity.CategoryEntity;

public record CategoryDto(
        int categoryId,
        String title
) {
    public static CategoryDto of(CategoryEntity entity) {
        return new CategoryDto(
                entity.getCategoryId(),
                entity.getTitle()
        );
    }

    public CategoryEntity toEntity(){
        return new CategoryEntity(
                categoryId,
                title,
                null
        );
    }
}
