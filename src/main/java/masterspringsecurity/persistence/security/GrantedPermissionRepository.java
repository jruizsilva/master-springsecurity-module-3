package masterspringsecurity.persistence.security;

import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantedPermissionRepository extends JpaRepository<GrantedPermissionEntity, Long> {
}