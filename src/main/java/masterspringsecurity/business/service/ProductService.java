package masterspringsecurity.business.service;

import masterspringsecurity.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductEntity> findAll();
    Page<ProductEntity> findAll(Pageable pageable);
    Optional<ProductEntity> findById(Long productId);
    ProductEntity save(ProductEntity productEntity);
    ProductEntity disableOneById(Long productId);
}
