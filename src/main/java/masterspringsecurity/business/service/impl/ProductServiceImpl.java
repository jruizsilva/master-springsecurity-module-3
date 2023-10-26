package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.ProductService;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.common.util.Status;
import masterspringsecurity.domain.dto.product.request.ProductRequest;
import masterspringsecurity.domain.entity.CategoryEntity;
import masterspringsecurity.domain.entity.ProductEntity;
import masterspringsecurity.persistence.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

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
    public ProductEntity update(Long productId,
                                ProductRequest productRequest) {
        ProductEntity productEntityFromDB =
                productRepository.findById(productId)
                                 .orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));

        productEntityFromDB.setName(productRequest.getName());
        productEntityFromDB.setPrice(productRequest.getPrice());

        CategoryEntity categoryEntity = CategoryEntity.builder()
                                                      .id(productRequest.getCategoryId())
                                                      .build();
        productEntityFromDB.setCategory(categoryEntity);
        return productRepository.save(productEntityFromDB);
    }

    @Override
    public ProductEntity disableOneById(Long productId) {
        ProductEntity productEntityFromDB =
                productRepository.findById(productId)
                                 .orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));
        productEntityFromDB.setStatus(Status.DISABLED);
        return productRepository.save(productEntityFromDB);
    }

}
