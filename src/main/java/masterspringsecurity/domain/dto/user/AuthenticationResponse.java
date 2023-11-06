package masterspringsecurity.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
    private String jwt;
}
