package iye.grupo2.cronicotrak.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FutureAppointmentDTO {
    private Integer id;
    private String patient;
    private String date;
    private String time;
    private String type;
    private String doctor;
    private String room;
    private String priority;
}
