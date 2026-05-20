package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recordatorio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recordatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private String tipo;
    private String canal;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    private String estado;
}
