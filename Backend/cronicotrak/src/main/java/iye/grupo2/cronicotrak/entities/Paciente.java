package iye.grupo2.cronicotrak.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iye.grupo2.cronicotrak.crypto.StringCryptoConverter;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringCryptoConverter.class)
    private String rut;

    @Convert(converter = StringCryptoConverter.class)
    private String nombre;

    private Integer age;
    private String status;
    private String room;

    @Convert(converter = StringCryptoConverter.class)
    @Column(name = "telefono")
    private String phone;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "next_visit")
    private LocalDateTime nextVisit;

    @Convert(converter = StringCryptoConverter.class)
    private String direccion;

    @Convert(converter = StringCryptoConverter.class)
    private String email;

    @Column(name = "fecha_proximo_retiro")
    private LocalDate fechaProximoRetiro;

    @Convert(converter = StringCryptoConverter.class)
    @Column(name = "tipo_sangre")
    private String tipoSangre;

    @Convert(converter = StringCryptoConverter.class)
    @Column(name = "nombre_emergencia")
    private String nombreEmergencia;

    @Convert(converter = StringCryptoConverter.class)
    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;

    @Convert(converter = StringCryptoConverter.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "paciente_alergia", joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "alergia")
    private List<String> alergias;

    private String estado;
    private String habitacion;

    @Builder.Default
    private Boolean activo = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private Establecimiento establecimiento;
}
