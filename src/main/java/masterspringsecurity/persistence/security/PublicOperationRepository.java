package masterspringsecurity.persistence.security;

import masterspringsecurity.domain.entity.PublicOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicOperationRepository extends JpaRepository<PublicOperationEntity, Long> {
}