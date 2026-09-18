package com.proyecto.vehiculos.repositories;

import com.proyecto.vehiculos.Entities.VehiculoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoPersonaRepository extends JpaRepository<VehiculoPersona, Long> {

    // Buscar las asociaciones por ID de la persona (conductor)
    List<VehiculoPersona> findByPersonaId(Long personaId);

    // Buscar las asociaciones por ID del vehículo
    List<VehiculoPersona> findByVehiculoId(Long vehiculoId);

    // Buscar asociaciones de conductores por estado exacto (ej. 'PO' - Puede Operar)
    List<VehiculoPersona> findByEstadoConductor(String estadoConductor);
}