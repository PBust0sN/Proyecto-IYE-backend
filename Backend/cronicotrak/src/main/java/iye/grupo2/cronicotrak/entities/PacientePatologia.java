package iye.grupo2.cronicotrak.entities;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patologia_id")
    private Patologia patologia;

    @Column(name = "fecha_diagnostico")
    private LocalDate fechaDiagnostico;
}
