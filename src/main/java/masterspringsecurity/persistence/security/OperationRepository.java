package masterspringsecurity.persistence.security;

import masterspringsecurity.domain.entity.security.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    @Query("select o from OperationEntity o where o.permitAll = true")
    List<OperationEntity> findByPermitAllTrue();
}