package masterspringsecurity.domain.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveUserRequest implements Serializable {
    @NotBlank
    @Size(min = 4)
    private String name;
    @NotBlank
    @Size(min = 4)
    private String username;
    @NotBlank
    @Size(min = 4)
    private String password;
    @NotBlank
    @Size(min = 4)
    private String repeatedPassword;
}
