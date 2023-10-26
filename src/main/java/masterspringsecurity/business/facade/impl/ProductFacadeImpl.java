package masterspringsecurity.business.facade.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.ProductFacade;
import masterspringsecurity.business.mapper.ProductMapper;
import masterspringsecurity.business.service.ProductService;
import masterspringsecurity.domain.dto.product.ProductDto;
import masterspringsecurity.domain.dto.product.request.ProductRequest;
import masterspringsecurity.domain.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        List<ProductEntity> productEntityList = productService.findAll();
        return productMapper.entityListToDtoList(productEntityList);
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<ProductEntity> productEntityPage = productService.findAll(pageable);
        return productEntityPage.map(productMapper::entityToDto);
    }

    @Override
    public Optional<ProductDto> findById(Long productId) {
        Optional<ProductEntity> productEntityOptional = productService.findById(productId);
        if (productEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        ProductDto productDto = productMapper.entityToDto(productEntityOptional.get());
        return Optional.of(productDto);
    }

    @Override
    public ProductDto create(ProductRequest productRequest) {
        ProductEntity productEntityToSave = productMapper.requestToEntity(productRequest);
        ProductEntity productEntitySaved = productService.save(productEntityToSave);
        return productMapper.entityToDto(productEntitySaved);
    }

    @Override
    public ProductDto update(Long productId,
                             ProductRequest productRequest) {
        ProductEntity productEntityToUpdate = productMapper.requestToEntity(productRequest);
        productEntityToUpdate.setId(productId);
        ProductEntity productEntityUpdated = productService.save(productEntityToUpdate);
        return productMapper.entityToDto(productEntityUpdated);
    }

    @Override
    public ProductDto disableOneById(Long productId) {
        ProductEntity productEntity = productService.disableOneById(productId);
        return productMapper.entityToDto(productEntity);
    }
}
