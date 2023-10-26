package masterspringsecurity.business.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import masterspringsecurity.business.service.JwtService;
import masterspringsecurity.domain.entity.UserEntity;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.expiration-minutes}")
    private Long EXPIRATION_MINUTES;

    @Override
    public String generateToken(UserEntity userEntity,
                                Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        /*60000ms = 1min*/
        Date expiration = new Date(issuedAt.getTime() + EXPIRATION_MINUTES * 60000);

        return Jwts.builder()
                   .claims(extraClaims)
                   .subject(userEntity.getUsername())
                   .issuedAt(issuedAt)
                   .expiration(expiration)
                   .header()
                   .add("typ",
                        "JWT")
                   .and()
                   .signWith(generateKey())
                   .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token)
                .getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(generateKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    private SecretKey generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println("mi clave es: " + new String(secretAsBytes));
        return Keys.hmacShaKeyFor(secretAsBytes);
    }
}
