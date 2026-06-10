package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sincronizacion_offline")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SincronizacionOffline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entidad;

    @Column(name = "entidad_id")
    private Long entidadId;

    private String operacion;
    private LocalDateTime fecha;
    private Boolean sincronizado;
}
