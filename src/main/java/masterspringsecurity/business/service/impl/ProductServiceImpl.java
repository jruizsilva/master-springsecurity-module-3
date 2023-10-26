package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.ProductService;
import masterspringsecurity.common.util.ProductStatus;
import masterspringsecurity.domain.entity.ProductEntity;
import masterspringsecurity.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductEntity> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public ProductEntity disableOneById(Long productId) {
        ProductEntity productEntity =
                productRepository.findById(productId)
                                 .orElseThrow(() -> new RuntimeException("Product not found"));
        productEntity.setStatus(ProductStatus.DISABLED);
        return productRepository.save(productEntity);
    }

}
