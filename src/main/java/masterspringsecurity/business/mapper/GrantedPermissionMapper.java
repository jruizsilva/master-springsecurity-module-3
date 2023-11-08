package masterspringsecurity.business.mapper;

import masterspringsecurity.domain.dto.grantedpermission.GrantedPermissionDto;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;

public interface GrantedPermissionMapper {
    GrantedPermissionDto entityToDto(GrantedPermissionEntity grantedPermissionEntity);
}
