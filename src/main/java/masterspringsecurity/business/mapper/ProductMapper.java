package masterspringsecurity.business.mapper;

import masterspringsecurity.domain.dto.product.ProductDto;
import masterspringsecurity.domain.dto.product.request.ProductRequest;
import masterspringsecurity.domain.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "status",
             ignore = true)
    @Mapping(target = "id",
             ignore = true)
    ProductEntity requestToEntity(ProductRequest productRequest);
    ProductDto entityToDto(ProductEntity productEntity);

    List<ProductDto> entityListToDtoList(List<ProductEntity> productEntityList);
}
