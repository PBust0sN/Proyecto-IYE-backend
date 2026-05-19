package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import lombok.*;

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
}
