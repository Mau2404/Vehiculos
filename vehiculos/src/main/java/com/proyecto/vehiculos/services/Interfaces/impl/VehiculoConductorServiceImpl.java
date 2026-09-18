package com.proyecto.vehiculos.services.Interfaces.impl;

import com.proyecto.vehiculos.dto.VehiculoConductorDTO;
import com.proyecto.vehiculos.Entities.Persona;
import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoPersona;
import com.proyecto.vehiculos.repositories.PersonaRepository;
import com.proyecto.vehiculos.repositories.VehiculoPersonaRepository;
import com.proyecto.vehiculos.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class VehiculoConductorServiceImpl {

    @Autowired
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public VehiculoPersona asociarVehiculoConductor(VehiculoConductorDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + dto.getVehiculoId()));

        Persona persona = personaRepository.findById(dto.getPersonaId())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada con ID: " + dto.getPersonaId()));

        // REGLA DE NEGOCIO: Solo se pueden asociar personas cuyo tipo sea CONDUCTOR (C)
        if (!"C".equalsIgnoreCase(persona.getTipoPersona())) {
            throw new IllegalArgumentException("La persona seleccionada no es un Conductor válido (Tipo actual: " + persona.getTipoPersona() + ")");
        }

        // Validar estado del conductor permitido
        String estado = dto.getEstadoConductor();
        if (estado == null || !estado.matches("^(PO|EA|RO)$")) {
            throw new IllegalArgumentException("Estado de conductor inválido. Valores permitidos: PO (Puede Operar), EA (Espera de Aprobación), RO (Restringido para Operar)[cite: 4].");
        }

        VehiculoPersona vp = new VehiculoPersona();
        vp.setVehiculo(vehiculo);
        vp.setPersona(persona);
        vp.setFechaAsociacion(dto.getFechaAsociacion() != null ? dto.getFechaAsociacion() : LocalDate.now());
        vp.setEstadoConductor(estado);

        return vehiculoPersonaRepository.save(vp);
    }

    @Transactional
    public VehiculoPersona cambiarEstadoConductor(Long asociacionId, String nuevoEstado) {
        VehiculoPersona vp = vehiculoPersonaRepository.findById(asociacionId)
                .orElseThrow(() -> new IllegalArgumentException("Asociación vehículo-conductor no encontrada con ID: " + asociacionId));

        if (nuevoEstado == null || !nuevoEstado.matches("^(PO|EA|RO)$")) {
            throw new IllegalArgumentException("Estado de conductor inválido. Valores permitidos: PO, EA, RO[cite: 4].");
        }

        vp.setEstadoConductor(nuevoEstado);
        return vehiculoPersonaRepository.save(vp);
    }
}