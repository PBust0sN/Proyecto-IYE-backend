package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.dtos.HistoricDataDto;
import iye.grupo2.cronicotrak.services.HistoricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historic")
@RequiredArgsConstructor
public class HistoricController {
    private final HistoricService service;

    @GetMapping("/get/historic")
    public ResponseEntity<List<HistoricDataDto>> getHistoric() {
        List<HistoricDataDto> data = service.getHistoricData();
        return ResponseEntity.ok(data);
    }
}
