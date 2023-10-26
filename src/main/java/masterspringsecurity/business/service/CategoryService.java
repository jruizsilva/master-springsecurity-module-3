package masterspringsecurity.business.service;

import masterspringsecurity.domain.dto.category.request.CategoryRequest;
import masterspringsecurity.domain.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<CategoryEntity> findAll(Pageable pageable);
    Optional<CategoryEntity> findById(Long categoryId);
    CategoryEntity save(CategoryEntity categoryEntity);
    CategoryEntity update(Long categoryId,
                          CategoryRequest categoryRequest);
    CategoryEntity disableOneById(Long categoryId);
}
