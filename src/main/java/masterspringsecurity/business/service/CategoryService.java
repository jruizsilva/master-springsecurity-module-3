package masterspringsecurity.business.service;

import masterspringsecurity.domain.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<CategoryEntity> findAll(Pageable pageable);
    Optional<CategoryEntity> findById(Long categoryId);
    CategoryEntity save(CategoryEntity categoryEntity);
    CategoryEntity disableOneById(Long categoryId);
}
