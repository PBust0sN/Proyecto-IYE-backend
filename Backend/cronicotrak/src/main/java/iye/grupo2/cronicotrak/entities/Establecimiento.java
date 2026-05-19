package iye.grupo2.cronicotrak.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "establecimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo;
    private String direccion;
    private String comuna;
    private String region;
}
