package masterspringsecurity.domain.dto.category.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryRequest {
    private String name;
}
