package iye.grupo2.cronicotrak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prediccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(name = "riesgo_descompensacion")
    private BigDecimal riesgoDescompensacion;

    @Column(name = "riesgo_inasistencia")
    private BigDecimal riesgoInasistencia;

    private String cluster;
    private LocalDateTime fecha;
}
