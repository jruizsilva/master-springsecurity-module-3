package masterspringsecurity.domain.entity.security;

import jakarta.persistence.*;
import lombok.*;
import masterspringsecurity.domain.entity.security.UserEntity;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "jwt_tokens")
public class JwtTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    @Column(length = 2048)
    private String token;
    private Date expirationDate;
    private Boolean isValid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}