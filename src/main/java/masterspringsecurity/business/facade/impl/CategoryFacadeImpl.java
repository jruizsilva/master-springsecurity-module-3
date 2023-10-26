package masterspringsecurity.business.facade.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.CategoryFacade;
import masterspringsecurity.business.mapper.CategoryMapper;
import masterspringsecurity.business.service.CategoryService;
import masterspringsecurity.common.util.Status;
import masterspringsecurity.domain.dto.category.CategoryDto;
import masterspringsecurity.domain.dto.category.request.CategoryRequest;
import masterspringsecurity.domain.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryFacadeImpl implements CategoryFacade {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        Page<CategoryEntity> categoryEntityPage = categoryService.findAll(pageable);
        return categoryEntityPage.map(categoryMapper::entityToDto);
    }

    @Override
    public Optional<CategoryDto> findById(Long categoryId) {
        Optional<CategoryEntity> categoryEntityOptional = categoryService.findById(categoryId);
        if (categoryEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        CategoryDto categoryDto = categoryMapper.entityToDto(categoryEntityOptional.get());
        return Optional.of(categoryDto);
    }

    @Override
    public CategoryDto create(CategoryRequest categoryRequest) {
        CategoryEntity categoryEntityToSave = categoryMapper.requestToEntity(categoryRequest);
        categoryEntityToSave.setStatus(Status.ENABLED);
        CategoryEntity categoryEntitySaved = categoryService.save(categoryEntityToSave);
        return categoryMapper.entityToDto(categoryEntitySaved);
    }

    @Override
    public CategoryDto update(Long categoryId,
                              CategoryRequest categoryRequest) {

        CategoryEntity categoryEntityUpdated = categoryService.update(categoryId,
                                                                      categoryRequest);
        return categoryMapper.entityToDto(categoryEntityUpdated);
    }

    @Override
    public CategoryDto disableOneById(Long productId) {
        CategoryEntity categoryEntity = categoryService.disableOneById(productId);
        return categoryMapper.entityToDto(categoryEntity);
    }
}
