package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.GrantedPermissionService;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import masterspringsecurity.persistence.security.GrantedPermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrantedPermissionServiceImpl implements GrantedPermissionService {
    private final GrantedPermissionRepository grantedPermissionRepository;

    @Override
    public Page<GrantedPermissionEntity> findAll(Pageable pageable) {
        return grantedPermissionRepository.findAll(pageable);
    }

    @Override
    public GrantedPermissionEntity findById(Long permissionId) {
        return grantedPermissionRepository.findById(permissionId)
                                          .orElseThrow(() -> new ObjectNotFoundException("Permission not found"));
    }
}
