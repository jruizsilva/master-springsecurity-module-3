package masterspringsecurity.business.mapper.impl;

import masterspringsecurity.business.mapper.GrantedPermissionMapper;
import masterspringsecurity.domain.dto.grantedpermission.GrantedPermissionDto;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class GrantedPermissionMapperImpl implements GrantedPermissionMapper {
    @Override
    public GrantedPermissionDto entityToDto(GrantedPermissionEntity grantedPermissionEntity) {
        return GrantedPermissionDto.builder()
                                   .id(grantedPermissionEntity.getId())
                                   .role(grantedPermissionEntity.getRole()
                                                                .getName())
                                   .module(grantedPermissionEntity.getOperation()
                                                                  .getModule()
                                                                  .getName())
                                   .operation(grantedPermissionEntity.getOperation()
                                                                     .getName())
                                   .httpMethod(grantedPermissionEntity.getOperation()
                                                                      .getHttpMethod())
                                   .build();
    }
}
