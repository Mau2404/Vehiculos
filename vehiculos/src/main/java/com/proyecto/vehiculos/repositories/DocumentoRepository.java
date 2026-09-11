package com.proyecto.vehiculos.repositories;

import com.proyecto.vehiculos.Entities.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
    // Método para buscar un documento paramétrico por su código único (ej: SOAT, RTM)
    Optional<Documento> findByCodigo(String codigo);
    
    // Método para validar existencia por código
    boolean existsByCodigo(String codigo);
}