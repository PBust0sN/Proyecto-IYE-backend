package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.dtos.HistoricDataDto;
import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.entities.Control;
import iye.grupo2.cronicotrak.entities.Prediccion;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import iye.grupo2.cronicotrak.repositories.PrediccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HistoricService {
    private final ControlRepository controlRepository;
    private final AlertaRepository alertaRepository;
    private final PrediccionRepository prediccionRepository;

    private static final String[] MESES = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

    public List<HistoricDataDto> getHistoricData() {
        List<HistoricDataDto> result = new ArrayList<>();

        // Contar controles controlados por mes
        Map<Integer, Integer> controladosMap = countControlledByMonth();

        // Contar descompensados por mes
        Map<Integer, Integer> descompensadosMap = countDecompensatedByMonth();

        // Contar alertas por mes
        Map<Integer, Integer> alertasMap = countAlertasByMonth();

        // Compilar datos para los 12 meses
        for (int i = 1; i <= 12; i++) {
            HistoricDataDto dto = HistoricDataDto.builder()
                    .mes(MESES[i - 1])
                    .controlados(controladosMap.getOrDefault(i, 0))
                    .descompensados(descompensadosMap.getOrDefault(i, 0))
                    .alertas(alertasMap.getOrDefault(i, 0))
                    .build();
            result.add(dto);
        }

        return result;
    }

    private Map<Integer, Integer> countControlledByMonth() {
        Map<Integer, Integer> map = new HashMap<>();
        List<Control> controls = controlRepository.findAllControlled();
        for (Control control : controls) {
            if (control.getFechaReal() != null) {
                int month = control.getFechaReal().getMonthValue();
                map.put(month, map.getOrDefault(month, 0) + 1);
            }
        }
        return map;
    }

    private Map<Integer, Integer> countDecompensatedByMonth() {
        Map<Integer, Integer> map = new HashMap<>();
        List<Prediccion> predictions = prediccionRepository.findAllDecompensated();
        for (Prediccion prediction : predictions) {
            if (prediction.getFecha() != null) {
                int month = prediction.getFecha().getMonthValue();
                map.put(month, map.getOrDefault(month, 0) + 1);
            }
        }
        return map;
    }

    private Map<Integer, Integer> countAlertasByMonth() {
        Map<Integer, Integer> map = new HashMap<>();
        List<Alerta> alertas = alertaRepository.findAllAlertas();
        for (Alerta alerta : alertas) {
            if (alerta.getFecha() != null) {
                int month = alerta.getFecha().getMonthValue();
                map.put(month, map.getOrDefault(month, 0) + 1);
            }
        }
        return map;
    }
}
