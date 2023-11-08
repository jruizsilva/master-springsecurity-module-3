package masterspringsecurity.domain.dto.user;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LogoutResponse implements Serializable {
    private String message;
}
