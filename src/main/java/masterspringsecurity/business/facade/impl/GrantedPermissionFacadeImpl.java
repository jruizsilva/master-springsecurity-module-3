package masterspringsecurity.business.facade.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.GrantedPermissionFacade;
import masterspringsecurity.business.service.GrantedPermissionService;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrantedPermissionFacadeImpl implements GrantedPermissionFacade {
    private final GrantedPermissionService grantedPermissionService;

    @Override
    public Page<GrantedPermissionEntity> findAll(Pageable pageable) {
        return grantedPermissionService.findAll(pageable);
    }
}
