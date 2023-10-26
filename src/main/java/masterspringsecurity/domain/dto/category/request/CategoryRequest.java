package masterspringsecurity.domain.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryRequest implements Serializable {
    @NotBlank
    private String name;
}
