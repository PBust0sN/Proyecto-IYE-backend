package iye.grupo2.cronicotrak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "indicador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Indicador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String unidad;
    private BigDecimal lower;
    private BigDecimal upper;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patologia_id")
    private Patologia patologia;
}
