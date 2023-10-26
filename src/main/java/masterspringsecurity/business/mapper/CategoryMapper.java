package masterspringsecurity.business.mapper;

import masterspringsecurity.domain.dto.category.CategoryDto;
import masterspringsecurity.domain.dto.category.request.CategoryRequest;
import masterspringsecurity.domain.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDto entityToDto(CategoryEntity categoryEntity);
    @Mapping(target = "status",
             ignore = true)
    @Mapping(target = "id",
             ignore = true)
    CategoryEntity requestToEntity(CategoryRequest categoryRequest);

    List<CategoryDto> entityToDtoList(List<CategoryEntity> categoryEntityList);
}
