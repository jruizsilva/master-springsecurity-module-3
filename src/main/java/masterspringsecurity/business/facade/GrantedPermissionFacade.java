package masterspringsecurity.business.facade;

import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrantedPermissionFacade {
    Page<GrantedPermissionEntity> findAll(Pageable pageable);
    GrantedPermissionEntity findById(Long permissionId);
}
