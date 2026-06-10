package iye.grupo2.cronicotrak.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDTO {
    private Long idPatient;
    private String name;
    private Integer age;
    private List<PathologyRecordDTO> condition;
    private String status;
    private String lastVisit;
    private String nextVisit;
    private String room;
    private String phone;
    private String mail;
    private String address;
    private String bloodType;
    private String emergencyName;
    private String emergencyPhone;
    private List<String> alergies;
    private List<String> actualMeds;
}
