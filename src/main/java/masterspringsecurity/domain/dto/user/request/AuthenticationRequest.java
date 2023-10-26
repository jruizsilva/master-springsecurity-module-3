package masterspringsecurity.domain.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthenticationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
