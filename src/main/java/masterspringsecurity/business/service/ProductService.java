package masterspringsecurity.business.service;

import masterspringsecurity.domain.dto.product.request.ProductRequest;
import masterspringsecurity.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<ProductEntity> findAll(Pageable pageable);
    Optional<ProductEntity> findById(Long productId);
    ProductEntity save(ProductEntity productEntity);
    ProductEntity update(Long productId,
                         ProductRequest productRequest);
    ProductEntity disableOneById(Long productId);
}
