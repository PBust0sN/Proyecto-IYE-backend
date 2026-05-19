package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "integracion_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntegracionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sistema;
    private String tipo;
    private String estado;
    private LocalDateTime fecha;
    private String detalle;
}
