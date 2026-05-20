package iye.grupo2.cronicotrak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
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

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String telefono;
    private String direccion;
    private String email;

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
    private Establecimiento establecimiento;
}
