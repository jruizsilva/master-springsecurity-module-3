package masterspringsecurity.persistence.security;

import masterspringsecurity.domain.entity.security.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    @Query("select o from OperationEntity o where o.permitAll = true")
    List<OperationEntity> findByPermitAllTrue();
    @Query("select o from OperationEntity o where upper(o.name) = upper(:name)")
    Optional<OperationEntity> findByNameIgnoreCase(@Param("name") String name);
}