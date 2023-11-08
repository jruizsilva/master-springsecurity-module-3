package masterspringsecurity.domain.dto.grantedpermission;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GrantedPermissionDto implements Serializable {
    private Long id;
    private String role;
    private String operation;
    private String module;
    private String httpMethod;
}
