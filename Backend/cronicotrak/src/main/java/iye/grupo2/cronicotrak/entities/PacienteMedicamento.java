package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    @JsonIgnore
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    @JsonIgnore
    private Medicamento medicamento;

    private String dosis;
    private String frecuencia;
}
