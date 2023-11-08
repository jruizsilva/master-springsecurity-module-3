package masterspringsecurity.business.service;

import jakarta.servlet.http.HttpServletRequest;
import masterspringsecurity.domain.entity.security.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public interface JwtService {
    String generateToken(UserEntity userEntity,
                         Map<String, Object> claims);

    String extractUsername(String token);
    String generateTokenv2(UserDetails userEntity,
                           Map<String, Object> extraClaims);
    String extractJwtFromRequest(HttpServletRequest request);
    Date getExpirationDateFromToken(String jwt);
}
