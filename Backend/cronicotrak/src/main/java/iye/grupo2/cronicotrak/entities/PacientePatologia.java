package iye.grupo2.cronicotrak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "paciente_patologia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacientePatologia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patologia_id")
    private Patologia patologia;

    @Column(name = "fecha_diagnostico")
    private LocalDate fechaDiagnostico;

    @Column(columnDefinition = "text")
    private String notas;

    @Column(name = "fecha_ultimo_control")
    private LocalDate fechaUltimoControl;
}
