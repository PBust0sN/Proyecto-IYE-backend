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
    private String patient;
    private String condition;
    private String alert;
    private String priority;
    private String time;
}
