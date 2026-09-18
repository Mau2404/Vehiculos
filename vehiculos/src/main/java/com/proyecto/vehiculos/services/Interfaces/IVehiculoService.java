package com.proyecto.vehiculos.services.Interfaces;

import com.proyecto.vehiculos.dto.CrearVehiculoDTO;
import com.proyecto.vehiculos.dto.DocumentoEntradaDTO;
import com.proyecto.vehiculos.dto.DocumentoPdfDTO;
import com.proyecto.vehiculos.dto.RespuestaVehiculoDTO;
import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoDocumento;

import java.util.List;

public interface IVehiculoService {
    RespuestaVehiculoDTO crearVehiculo(CrearVehiculoDTO dto);
    List<Vehiculo> obtenerTodos();
    RespuestaVehiculoDTO obtenerPorId(Long id);
    Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculoActualizado);
    void eliminarVehiculo(Long id);
    
    // Consultas específicas requeridas
    Vehiculo obtenerPorPlaca(String placa);
    List<Vehiculo> obtenerPorTipoVehiculo(String tipoVehiculo);
    List<Vehiculo> obtenerPorDocumentoComun(Long documentoId);
    List<Vehiculo> obtenerPorEstadoDocumento(String estado);
    
    // Servicio de asociación de documento adicional
    VehiculoDocumento asociarDocumento(Long vehiculoId, DocumentoEntradaDTO dto);
    List<VehiculoDocumento> cargarDocumentosPdf(Long id, List<DocumentoPdfDTO> listaDtos);
}