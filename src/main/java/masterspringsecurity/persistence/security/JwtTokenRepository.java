package masterspringsecurity.persistence.security;

import masterspringsecurity.domain.entity.security.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {
    @Query("select j from JwtTokenEntity j where j.token = :token")
    Optional<JwtTokenEntity> findByToken(@Param("token") String token);
}