package masterspringsecurity.business.service;

import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrantedPermissionService {
    Page<GrantedPermissionEntity> findAll(Pageable pageable);
    GrantedPermissionEntity findById(Long permissionId);
}
