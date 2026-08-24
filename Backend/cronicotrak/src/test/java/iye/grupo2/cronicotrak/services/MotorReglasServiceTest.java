package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.entities.Indicador;
import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import iye.grupo2.cronicotrak.repositories.MedicionRepository;
import iye.grupo2.cronicotrak.repositories.PacientePatologiaRepository;
import iye.grupo2.cronicotrak.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

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

    @Mock
    private MedicionRepository medicionRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PacientePatologiaRepository pacientePatologiaRepository;

    @InjectMocks
    private MotorReglasService motorReglasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void evaluarReglasPorMedicion_WhenCritica_GeneratesAlertAndWhatsApp() {
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
        medicion.setValor(new BigDecimal("140")); // Critica, > 120

        when(alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(1L, "Critica")).thenReturn(false);

        // Act
        motorReglasService.evaluarReglasPorMedicion(medicion);

        // Assert
        ArgumentCaptor<Alerta> alertaCaptor = ArgumentCaptor.forClass(Alerta.class);
        verify(alertaRepository, times(1)).save(alertaCaptor.capture());
        
        Alerta savedAlerta = alertaCaptor.getValue();
        assertEquals("Critica", savedAlerta.getTipo());
        assertTrue(savedAlerta.getDescripcion().contains("Lectura crítica"));

        ArgumentCaptor<String> mensajeCaptor = ArgumentCaptor.forClass(String.class);
        verify(whatsAppService, times(1)).enviarMensaje(eq("56912345678"), mensajeCaptor.capture());
        assertTrue(mensajeCaptor.getValue().contains("valor crítico"));
    }

    @Test
    void evaluarReglasPorMedicion_WhenCriticaAndAlertExists_DoesNotGenerateAlert() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Indicador indicador = new Indicador();
        indicador.setNombre("Presion Arterial Sistolica");
        indicador.setLower(new BigDecimal("90"));
        indicador.setUpper(new BigDecimal("120"));

        Medicion medicion = new Medicion();
        medicion.setPaciente(paciente);
        medicion.setIndicador(indicador);
        medicion.setValor(new BigDecimal("140")); // Critica, > 120

        when(alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(1L, "Critica")).thenReturn(true);

        // Act
        motorReglasService.evaluarReglasPorMedicion(medicion);

        // Assert
        verify(alertaRepository, never()).save(any(Alerta.class));
        verify(whatsAppService, never()).enviarMensaje(anyString(), anyString());
    }

    @Test
    void evaluarReglasPorMedicion_WhenTendenciaAlza_GeneratesDeterioroAlertAndWhatsApp() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Ana Lopez");
        paciente.setPhone("56987654321");

        Indicador indicador = new Indicador();
        indicador.setId(10L);
        indicador.setNombre("Glucosa");
        indicador.setLower(new BigDecimal("70"));
        indicador.setUpper(new BigDecimal("140"));

        Medicion m1 = new Medicion();
        m1.setValor(new BigDecimal("100"));
        m1.setFecha(LocalDate.now().minusDays(30));

        Medicion m2 = new Medicion();
        m2.setValor(new BigDecimal("110"));
        m2.setFecha(LocalDate.now().minusDays(15));

        Medicion m3 = new Medicion();
        m3.setPaciente(paciente);
        m3.setIndicador(indicador);
        m3.setValor(new BigDecimal("120")); // Normal pero tendencia alza (100 -> 110 -> 120)
        m3.setFecha(LocalDate.now());

        when(medicionRepository.findByPacienteIdAndIndicadorId(1L, 10L)).thenReturn(Arrays.asList(m1, m2, m3));
        when(alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(1L, "Deterioro")).thenReturn(false);

        // Act
        motorReglasService.evaluarReglasPorMedicion(m3);

        // Assert
        ArgumentCaptor<Alerta> alertaCaptor = ArgumentCaptor.forClass(Alerta.class);
        verify(alertaRepository, times(1)).save(alertaCaptor.capture());
        
        Alerta savedAlerta = alertaCaptor.getValue();
        assertEquals("Deterioro", savedAlerta.getTipo());
        assertTrue(savedAlerta.getDescripcion().contains("Tendencia preocupante"));

        ArgumentCaptor<String> mensajeCaptor = ArgumentCaptor.forClass(String.class);
        verify(whatsAppService, times(1)).enviarMensaje(eq("56987654321"), mensajeCaptor.capture());
        assertTrue(mensajeCaptor.getValue().contains("tendencia preocupante"));
    }

    @Test
    void evaluarReglasPorMedicion_WhenNormal_DoesNotGenerateAlert() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Indicador indicador = new Indicador();
        indicador.setId(10L);
        indicador.setLower(new BigDecimal("90"));
        indicador.setUpper(new BigDecimal("120"));

        Medicion medicion = new Medicion();
        medicion.setPaciente(paciente);
        medicion.setIndicador(indicador);
        medicion.setValor(new BigDecimal("100")); // Normal
        medicion.setFecha(LocalDate.now());

        when(medicionRepository.findByPacienteIdAndIndicadorId(1L, 10L)).thenReturn(Arrays.asList(medicion));

        // Act
        motorReglasService.evaluarReglasPorMedicion(medicion);

        // Assert
        verify(alertaRepository, never()).save(any(Alerta.class));
        verify(whatsAppService, never()).enviarMensaje(anyString(), anyString());
    }
}
