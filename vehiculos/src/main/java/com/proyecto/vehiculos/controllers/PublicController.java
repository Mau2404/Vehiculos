package com.proyecto.vehiculos.controllers;

import com.proyecto.vehiculos.Entities.Persona;
import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.services.Interfaces.impl.PublicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private PublicServiceImpl publicService;

    // A. Vehículos con documentos vencidos[cite: 4]
    @GetMapping("/vehiculos/vencidos")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosVencidos() {
        return ResponseEntity.ok(publicService.obtenerVehiculosConDocumentosVencidos());
    }

    // B. Conductores que pueden operar ('PO')[cite: 4]
    @GetMapping("/conductores/operativos")
    public ResponseEntity<List<Persona>> obtenerConductoresOperativos() {
        return ResponseEntity.ok(publicService.obtenerConductoresQuePuedenOperar());
    }

    // C. Vehículo por placa con conductores y documentos[cite: 4]
    @GetMapping("/vehiculos/placa/{placa}")
    public ResponseEntity<Map<String, Object>> obtenerVehiculoDetallado(@PathVariable String placa) {
        return ResponseEntity.ok(publicService.obtenerVehiculoDetalladoPorPlaca(placa));
    }

    // D. Vehículos con documentos próximos a vencer (Parámetro de días)[cite: 4]
    @GetMapping("/vehiculos/proximos-vencer")
    public ResponseEntity<List<Vehiculo>> obtenerVehiculosProximosAVencer(@RequestParam int dias) {
        return ResponseEntity.ok(publicService.obtenerVehiculosProximosAVencer(dias));
    }

    // E. Total de personas agrupadas por tipo[cite: 4]
    @GetMapping("/personas/agrupadas")
    public ResponseEntity<Map<String, Long>> obtenerPersonasAgrupadas() {
        return ResponseEntity.ok(publicService.obtenerTotalPersonasAgrupadas());
    }
}