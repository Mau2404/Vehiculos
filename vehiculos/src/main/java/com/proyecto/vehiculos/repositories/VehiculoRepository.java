package com.proyecto.vehiculos.repositories;

import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoDocumento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // 1. Buscar vehículo por número de placa (Requerimiento)
    Optional<Vehiculo> findByPlaca(String placa);

    // Validar si existe una placa
    boolean existsByPlaca(String placa);

    // 2. Buscar vehículos por tipo de vehículo (Automovil / Motocicleta) (Requerimiento)
    List<Vehiculo> findByTipoVehiculo(String tipoVehiculo);

    // 3. Consulta JPQL: Buscar vehículos que tengan en común un tipo de documento específico (Requerimiento)
    @Query("SELECT DISTINCT vd.vehiculo FROM VehiculoDocumento vd WHERE vd.documento.id = :documentoId")
    List<Vehiculo> findVehiculosByDocumentoId(@Param("documentoId") Long documentoId);

    // 4. Consulta JPQL: Buscar vehículos según el estado del documento asociado ('Habilitado', 'Vencido', 'En Verificacion') (Requerimiento)
    @Query("SELECT DISTINCT vd.vehiculo FROM VehiculoDocumento vd WHERE vd.estado = :estado")
    List<Vehiculo> findVehiculosByEstadoDocumento(@Param("estado") String estado);

    // Buscar documentos cuya fecha de vencimiento sea anterior a la fecha actual (Vencidos)
    List<VehiculoDocumento> findByFechaVencimientoBefore(LocalDate fecha);

    // Buscar documentos que vencen entre la fecha actual y una fecha límite (Próximos a vencer)
    List<VehiculoDocumento> findByFechaVencimientoBetween(LocalDate inicio, LocalDate fin);
    
}