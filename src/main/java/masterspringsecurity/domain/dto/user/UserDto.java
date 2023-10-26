package masterspringsecurity.domain.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import masterspringsecurity.common.util.Role;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
}
