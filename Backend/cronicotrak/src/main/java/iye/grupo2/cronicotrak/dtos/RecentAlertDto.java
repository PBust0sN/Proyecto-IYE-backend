package iye.grupo2.cronicotrak.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentAlertDto {
    private Long id;
    private String patientName;
    private String type;
    private String description;
    private String time;
    private String status;
}
