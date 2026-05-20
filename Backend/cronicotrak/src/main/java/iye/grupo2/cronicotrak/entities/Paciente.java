package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;
    private String nombre;
    private Integer age;
    private String status;
    private String room;

    @Column(name = "telefono")
    private String phone;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "next_visit")
    private LocalDateTime nextVisit;

    private String direccion;

    @ManyToOne
    @JoinColumn(name = "establecimiento_id")
    @JsonIgnore
    private Establecimiento establecimiento;
}
