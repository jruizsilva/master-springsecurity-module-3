package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.RoleService;
import masterspringsecurity.domain.entity.security.RoleEntity;
import masterspringsecurity.persistence.security.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Value("${security.default.role}")
    private String defaultRole;

    @Override
    public Optional<RoleEntity> findDefaultRole() {
        return roleRepository.findByNameIgnoreCase(defaultRole);
    }
}
