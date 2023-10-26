package masterspringsecurity.business.facade;

import masterspringsecurity.domain.dto.product.ProductDto;
import masterspringsecurity.domain.dto.product.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductFacade {
    Page<ProductDto> findAll(Pageable pageable);
    Optional<ProductDto> findById(Long productId);
    ProductDto create(ProductRequest productRequest);
    ProductDto update(Long productId,
                      ProductRequest productRequest);
    ProductDto disableOneById(Long productId);
}
