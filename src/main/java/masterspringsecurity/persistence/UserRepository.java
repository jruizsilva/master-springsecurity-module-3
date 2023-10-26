package masterspringsecurity.persistence;

import masterspringsecurity.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where upper(u.username) = upper(:username)")
    Optional<UserEntity> findByUsernameIgnoreCase(@Param("username") String username);
}