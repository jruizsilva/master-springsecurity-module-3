package masterspringsecurity.domain.dto.category;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {
    private Long id;
    private String name;
}
