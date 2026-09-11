package com.proyecto.vehiculos.repositories;

import com.proyecto.vehiculos.Entities.VehiculoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoDocumentoRepository extends JpaRepository<VehiculoDocumento, Long> {

    // Buscar todos los documentos asociados a un vehículo en particular
    List<VehiculoDocumento> findByVehiculoId(Long vehiculoId);

    // Buscar la relación por ID de vehículo e ID de documento
    boolean existsByVehiculoIdAndDocumentoId(Long vehiculoId, Long documentoId);
}