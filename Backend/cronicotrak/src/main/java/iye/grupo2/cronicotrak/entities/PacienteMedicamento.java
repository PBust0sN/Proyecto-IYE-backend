package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paciente_medicamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteMedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    private String dosis;
    private String frecuencia;
}
