package masterspringsecurity.domain.dto.grantedpermission.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GrantedPermissionRequest implements Serializable {
    @NotBlank
    private String roleName;
    @NotBlank
    private String permissionName;
}
