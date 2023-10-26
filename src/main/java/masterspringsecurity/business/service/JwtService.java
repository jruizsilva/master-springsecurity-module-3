package masterspringsecurity.business.service;

import org.springframework.stereotype.Service;
import masterspringsecurity.domain.entity.UserEntity;

import java.util.Map;

@Service
public interface JwtService {
    String generateToken(UserEntity userEntity,
                         Map<String, Object> claims);

    String extractUsername(String token);
}
