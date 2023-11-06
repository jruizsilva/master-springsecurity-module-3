package masterspringsecurity.domain.dto.user;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisteredUserDto implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;
}
