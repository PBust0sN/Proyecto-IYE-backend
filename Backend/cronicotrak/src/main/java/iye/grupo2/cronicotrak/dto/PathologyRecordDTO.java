package iye.grupo2.cronicotrak.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PathologyRecordDTO {
    private Long id;
    private String name;
    private String description;
    private List<IndicatorDTO> indicators;
    private String lastUpdate;
    private String notes;
}
