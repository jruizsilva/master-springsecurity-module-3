package masterspringsecurity.business.service;

import masterspringsecurity.domain.entity.security.RoleEntity;

import java.util.Optional;

public interface RoleService {
    Optional<RoleEntity> findDefaultRole();
}
