package masterspringsecurity.presentation.advice;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError implements Serializable {
    private String backendMessage;
    private String message;
    private String url;
    private String method;
    private LocalDateTime timestamp;
}
