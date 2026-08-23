package iye.grupo2.cronicotrak.DTO;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicatorDTO {
    private Long id;
    private String name;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal lower;
    private BigDecimal upper;
    private String state;
}
