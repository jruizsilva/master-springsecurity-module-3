package masterspringsecurity.domain.dto.grantedpermission.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GrantedPermissionRequest {
    private String roleName;
    private String permissionName;
}
