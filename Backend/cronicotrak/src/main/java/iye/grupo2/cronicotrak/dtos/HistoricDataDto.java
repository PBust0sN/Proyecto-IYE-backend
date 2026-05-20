package iye.grupo2.cronicotrak.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricDataDto {
    private String mes;
    private Integer controlados;
    private Integer descompensados;
    private Integer alertas;
}
