package masterspringsecurity.domain.dto.user.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import masterspringsecurity.common.util.RoleEnum;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;
}
