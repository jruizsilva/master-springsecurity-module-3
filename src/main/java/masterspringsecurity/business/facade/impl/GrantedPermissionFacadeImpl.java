package masterspringsecurity.business.facade.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.GrantedPermissionFacade;
import masterspringsecurity.business.mapper.GrantedPermissionMapper;
import masterspringsecurity.business.service.GrantedPermissionService;
import masterspringsecurity.domain.dto.grantedpermission.GrantedPermissionDto;
import masterspringsecurity.domain.dto.grantedpermission.request.GrantedPermissionRequest;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrantedPermissionFacadeImpl implements GrantedPermissionFacade {
    private final GrantedPermissionService grantedPermissionService;
    private final GrantedPermissionMapper grantedPermissionMapper;

    @Override
    public Page<GrantedPermissionDto> findAll(Pageable pageable) {
        Page<GrantedPermissionEntity> grantedPermissionEntityPage = grantedPermissionService.findAll(pageable);
        return grantedPermissionEntityPage.map(grantedPermissionMapper::entityToDto);
    }

    @Override
    public GrantedPermissionDto findById(Long permissionId) {
        GrantedPermissionEntity grantedPermissionEntity = grantedPermissionService.findById(permissionId);
        return grantedPermissionMapper.entityToDto(grantedPermissionEntity);
    }

    @Override
    public Void deleteOneById(Long permissionId) {
        return grantedPermissionService.deleteOneById(permissionId);
    }

    @Override
    public GrantedPermissionDto createOne(GrantedPermissionRequest grantedPermissionRequest) {
        GrantedPermissionEntity grantedPermissionEntity = grantedPermissionService.createOne(grantedPermissionRequest);
        return grantedPermissionMapper.entityToDto(grantedPermissionEntity);

    }
}
