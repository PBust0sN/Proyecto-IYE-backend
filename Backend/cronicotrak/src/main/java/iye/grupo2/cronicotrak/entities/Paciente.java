package iye.grupo2.cronicotrak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private String email;

    @ManyToOne
    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @Column(name = "nombre_emergencia")
    private String nombreEmergencia;

    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "paciente_alergia", joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "alergia")
    private List<String> alergias;

    private String estado;
    private String habitacion;

    @Builder.Default
    private Boolean activo = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    @JsonIgnore
    private Establecimiento establecimiento;
}
