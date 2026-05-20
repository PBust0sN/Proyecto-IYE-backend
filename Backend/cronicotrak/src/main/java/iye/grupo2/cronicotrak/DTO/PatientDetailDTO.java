package iye.grupo2.cronicotrak.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDetailDTO {
    private Long id;
    private String name;
    private Integer age;
    private List<String> condition;
    private List<String> alertLevel;
    private List<String> pattern;
    private String lastMeasurement;
    private String room;
    private String phone;
}
