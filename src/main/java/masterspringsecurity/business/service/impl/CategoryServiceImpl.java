package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.CategoryService;
import masterspringsecurity.common.util.Status;
import masterspringsecurity.domain.entity.CategoryEntity;
import masterspringsecurity.persistence.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryEntity> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<CategoryEntity> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity disableOneById(Long categoryId) {
        CategoryEntity categoryEntity =
                categoryRepository.findById(categoryId)
                                  .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryEntity.setStatus(Status.DISABLED);
        return categoryRepository.save(categoryEntity);
    }
}
