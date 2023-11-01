package masterspringsecurity.domain.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import masterspringsecurity.domain.entity.CategoryEntity;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String name;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull
    private CategoryEntity category;
}
