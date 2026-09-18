package com.proyecto.vehiculos.services.Interfaces.impl;

import com.proyecto.vehiculos.Entities.*;
import com.proyecto.vehiculos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PublicServiceImpl {

    @Autowired
    private VehiculoDocumentoRepository vehiculoDocumentoRepository;

    @Autowired
    private VehiculoPersonaRepository vehiculoPersonaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    // 1. Consultar vehículos que tengan documentos vencidos
    public List<Vehiculo> obtenerVehiculosConDocumentosVencidos() {
        LocalDate hoy = LocalDate.now();
        List<VehiculoDocumento> docsVencidos = vehiculoDocumentoRepository.findByFechaVencimientoBefore(hoy);
        return docsVencidos.stream()
                .map(VehiculoDocumento::getVehiculo)
                .distinct()
                .collect(Collectors.toList());
    }

    // 2. Consultar todos los conductores que puedan operar ('PO')
    public List<Persona> obtenerConductoresQuePuedenOperar() {
        List<VehiculoPersona> asociaciones = vehiculoPersonaRepository.findByEstadoConductor("PO");
        return asociaciones.stream()
                .map(VehiculoPersona::getPersona)
                .distinct()
                .collect(Collectors.toList());
    }

    // 3. Consultar vehículo por placa con sus conductores y documentos asociados[cite: 4]
    public Map<String, Object> obtenerVehiculoDetalladoPorPlaca(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con la placa: " + placa));

        List<VehiculoDocumento> documentos = vehiculoDocumentoRepository.findByVehiculoId(vehiculo.getId());
        List<VehiculoPersona> conductores = vehiculoPersonaRepository.findByVehiculoId(vehiculo.getId());

        return Map.of(
                "vehiculo", vehiculo,
                "documentos", documentos,
                "conductoresAsociados", conductores
        );
    }

    // 4. Consultar vehículos con documentos próximos a vencer según parámetro de días[cite: 4]
    public List<Vehiculo> obtenerVehiculosProximosAVencer(int dias) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(dias);
        List<VehiculoDocumento> docsProximos = vehiculoDocumentoRepository.findByFechaVencimientoBetween(hoy, fechaLimite);
        return docsProximos.stream()
                .map(VehiculoDocumento::getVehiculo)
                .distinct()
                .collect(Collectors.toList());
    }

    // 5. Consultar el total de personas agrupadas por tipo[cite: 4]
    public Map<String, Long> obtenerTotalPersonasAgrupadas() {
        List<Persona> personas = personaRepository.findAll();
        long conductores = personas.stream().filter(p -> "C".equalsIgnoreCase(p.getTipoPersona())).count();
        long administrativos = personas.stream().filter(p -> "A".equalsIgnoreCase(p.getTipoPersona())).count();

        return Map.of(
                "CONDUCTORES", conductores,
                "ADMINISTRATIVOS", administrativos
        );
    }
}