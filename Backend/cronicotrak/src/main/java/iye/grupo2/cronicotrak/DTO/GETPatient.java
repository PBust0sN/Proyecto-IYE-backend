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
public class GETPatient {
    private Long id;
    private String name;
    private Integer age;
    private List<String> condition;
    private String status;
    private String lastVisit;
    private String nextVisit;
    private String room;
    private String phone;
}
