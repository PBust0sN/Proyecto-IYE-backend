package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "medicion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "indicador_id")
    private Indicador indicador;

    private BigDecimal valor;
    private LocalDate fecha;
}
