package com.proyecto.vehiculos.controllers;

import com.proyecto.vehiculos.dto.EstadoConductorDTO;
import com.proyecto.vehiculos.dto.VehiculoConductorDTO;
import com.proyecto.vehiculos.Entities.VehiculoPersona;
import com.proyecto.vehiculos.services.Interfaces.impl.VehiculoConductorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoConductorController {

    @Autowired
    private VehiculoConductorServiceImpl vehiculoConductorService;

    // 1. POST: Asociar vehículos a un conductor específico
    @PostMapping("/conductores")
    public ResponseEntity<VehiculoPersona> asociarVehiculoConductor(@RequestBody VehiculoConductorDTO dto) {
        VehiculoPersona nuevaAsociacion = vehiculoConductorService.asociarVehiculoConductor(dto);
        return new ResponseEntity<>(nuevaAsociacion, HttpStatus.CREATED);
    }

    // 2. PUT: Cambiar el estado del conductor en relación con el vehículo (PO, EA, RO)
    @PutMapping("/conductores/{id}/estado")
    public ResponseEntity<VehiculoPersona> cambiarEstadoConductor(@PathVariable Long id, @RequestBody EstadoConductorDTO dto) {
        VehiculoPersona asociacionActualizada = vehiculoConductorService.cambiarEstadoConductor(id, dto.getEstadoConductor());
        return ResponseEntity.ok(asociacionActualizada);
    }
}