package masterspringsecurity.business.facade;

import masterspringsecurity.domain.dto.grantedpermission.GrantedPermissionDto;
import masterspringsecurity.domain.dto.grantedpermission.request.GrantedPermissionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrantedPermissionFacade {
    Page<GrantedPermissionDto> findAll(Pageable pageable);
    GrantedPermissionDto findById(Long permissionId);
    Void deleteOneById(Long permissionId);
    GrantedPermissionDto createOne(GrantedPermissionRequest grantedPermissionRequest);
}
