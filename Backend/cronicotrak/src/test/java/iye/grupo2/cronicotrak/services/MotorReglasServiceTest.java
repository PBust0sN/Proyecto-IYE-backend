package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.entities.Indicador;
import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MotorReglasServiceTest {

    @Mock
    private ControlRepository controlRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @Mock
    private WhatsAppService whatsAppService;

    @InjectMocks
    private MotorReglasService motorReglasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void evaluarReglasPorMedicion_WhenAlteredAndMissedControl_GeneratesAlertAndWhatsApp() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Juan Perez");
        paciente.setPhone("56912345678");

        Indicador indicador = new Indicador();
        indicador.setNombre("Presion Arterial Sistolica");
        indicador.setUnidad("mmHg");
        indicador.setLower(new BigDecimal("90"));
        indicador.setUpper(new BigDecimal("120"));

        Medicion medicion = new Medicion();
        medicion.setPaciente(paciente);
        medicion.setIndicador(indicador);
        medicion.setValor(new BigDecimal("140")); // Altered, > 120

        when(controlRepository.existsByPacienteIdAndAsistioFalse(1L)).thenReturn(true);

        // Act
        motorReglasService.evaluarReglasPorMedicion(medicion);

        // Assert
        ArgumentCaptor<Alerta> alertaCaptor = ArgumentCaptor.forClass(Alerta.class);
        verify(alertaRepository, times(1)).save(alertaCaptor.capture());
        
        Alerta savedAlerta = alertaCaptor.getValue();
        assertEquals("Preventiva", savedAlerta.getTipo());
        assertTrue(savedAlerta.getDescripcion().contains("Juan Perez"));
        assertTrue(savedAlerta.getDescripcion().contains("inasistencia"));
        assertTrue(savedAlerta.getDescripcion().contains("140"));

        verify(whatsAppService, times(1)).enviarMensaje(eq("56912345678"), anyString());
    }

    @Test
    void evaluarReglasPorMedicion_WhenNormal_DoesNotGenerateAlert() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Indicador indicador = new Indicador();
        indicador.setLower(new BigDecimal("90"));
        indicador.setUpper(new BigDecimal("120"));

        Medicion medicion = new Medicion();
        medicion.setPaciente(paciente);
        medicion.setIndicador(indicador);
        medicion.setValor(new BigDecimal("100")); // Normal

        // Act
        motorReglasService.evaluarReglasPorMedicion(medicion);

        // Assert
        verify(controlRepository, never()).existsByPacienteIdAndAsistioFalse(anyLong());
        verify(alertaRepository, never()).save(any(Alerta.class));
        verify(whatsAppService, never()).enviarMensaje(anyString(), anyString());
    }
}
