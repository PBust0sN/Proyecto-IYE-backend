package iye.grupo2.cronicotrak.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientQuantityDTO {
    private String type;
    private Long quantity;
}
