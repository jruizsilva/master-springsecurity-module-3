package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.GrantedPermissionService;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.domain.dto.grantedpermission.request.GrantedPermissionRequest;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import masterspringsecurity.domain.entity.security.OperationEntity;
import masterspringsecurity.domain.entity.security.RoleEntity;
import masterspringsecurity.persistence.security.GrantedPermissionRepository;
import masterspringsecurity.persistence.security.OperationRepository;
import masterspringsecurity.persistence.security.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrantedPermissionServiceImpl implements GrantedPermissionService {
    private final GrantedPermissionRepository grantedPermissionRepository;
    private final RoleRepository roleRepository;
    private final OperationRepository operationRepository;

    @Override
    public Page<GrantedPermissionEntity> findAll(Pageable pageable) {
        return grantedPermissionRepository.findAll(pageable);
    }

    @Override
    public GrantedPermissionEntity findById(Long permissionId) {
        return grantedPermissionRepository.findById(permissionId)
                                          .orElseThrow(() -> new ObjectNotFoundException("Permission not found"));
    }

    @Override
    public Void deleteOneById(Long permissionId) {
        if (!grantedPermissionRepository.existsById(permissionId)) {
            throw new ObjectNotFoundException("Permission to delete not found");
        }
        grantedPermissionRepository.deleteById(permissionId);
        return null;
    }

    @Override
    public GrantedPermissionEntity createOne(GrantedPermissionRequest grantedPermissionRequest) {
        RoleEntity roleEntity =
                roleRepository.findByNameIgnoreCase(grantedPermissionRequest.getRoleName())
                              .orElseThrow(() -> new ObjectNotFoundException("Role not found"));
        OperationEntity operationEntity =
                operationRepository.findByNameIgnoreCase(grantedPermissionRequest.getPermissionName())
                                   .orElseThrow(() -> new ObjectNotFoundException("Operation not found"));
        GrantedPermissionEntity grantedPermissionEntityToSave =
                GrantedPermissionEntity.builder()
                                       .role(roleEntity)
                                       .operation(operationEntity)
                                       .build();
        return grantedPermissionRepository.save(grantedPermissionEntityToSave);
    }
}
