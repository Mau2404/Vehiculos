package com.proyecto.vehiculos.repositories;

import com.proyecto.vehiculos.Entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    
    // Buscar persona por número de identificación único
    Optional<Persona> findByIdentificacion(String identificacion);
    
    // Validar existencia previa por identificación
    boolean existsByIdentificacion(String identificacion);
}