package masterspringsecurity.domain.dto.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductRequest implements Serializable {
    @NotBlank(message = "field name is required")
    private String name;
    @NotNull
    @DecimalMin(value = "0.01",
                message = "field price is required")
    private BigDecimal price;
    @NotNull(message = "field categoryId is required")
    @Min(value = 1)
    private Long categoryId;
}
