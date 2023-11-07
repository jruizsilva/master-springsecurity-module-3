package masterspringsecurity.persistence.security;

import masterspringsecurity.domain.entity.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("select r from RoleEntity r where upper(r.name) = upper(:name)")
    Optional<RoleEntity> findByNameIgnoreCase(@Param("name") String name);
}