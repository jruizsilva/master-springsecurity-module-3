package masterspringsecurity.business.facade;

import masterspringsecurity.domain.dto.category.CategoryDto;
import masterspringsecurity.domain.dto.category.request.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryFacade {
    Page<CategoryDto> findAll(Pageable pageable);
    Optional<CategoryDto> findById(Long productId);
    CategoryDto create(CategoryRequest productRequest);
    CategoryDto update(Long productId,
                       CategoryRequest productRequest);
    CategoryDto disableOneById(Long productId);
}
