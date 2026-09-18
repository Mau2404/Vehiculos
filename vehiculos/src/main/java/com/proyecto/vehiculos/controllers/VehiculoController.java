package com.proyecto.vehiculos.controllers;

import com.proyecto.vehiculos.dto.CrearVehiculoDTO;
import com.proyecto.vehiculos.dto.DocumentoEntradaDTO;
import com.proyecto.vehiculos.dto.DocumentoPdfDTO;
import com.proyecto.vehiculos.dto.RespuestaVehiculoDTO;
import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoDocumento;
import com.proyecto.vehiculos.services.Interfaces.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;

    // 1. Crear vehículo (con validación manual y estado forzado "En Verificacion")
    @PostMapping
    public ResponseEntity<RespuestaVehiculoDTO> crearVehiculo(@RequestBody CrearVehiculoDTO dto) {
        RespuestaVehiculoDTO respuesta = vehiculoService.crearVehiculo(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // 2. Obtener todos los vehículos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    // 3. Obtener vehículo por ID (retorna con sus documentos)
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaVehiculoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.obtenerPorId(id));
    }

    // 4. Actualizar vehículo
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.actualizarVehiculo(id, vehiculo));
    }

    // 5. Eliminar vehículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }

    // 6. REQUERIMIENTO: Buscar vehículo por número de placa
    @GetMapping("/placa/{placa}")
    public ResponseEntity<Vehiculo> obtenerPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(vehiculoService.obtenerPorPlaca(placa));
    }

    // 7. REQUERIMIENTO: Buscar vehículos por tipo (Automovil / Motocicleta)
    @GetMapping("/tipo/{tipoVehiculo}")
    public ResponseEntity<List<Vehiculo>> obtenerPorTipoVehiculo(@PathVariable String tipoVehiculo) {
        return ResponseEntity.ok(vehiculoService.obtenerPorTipoVehiculo(tipoVehiculo));
    }

    // 8. REQUERIMIENTO: Buscar vehículos por tipo de documento común
    @GetMapping("/documento-comun/{documentoId}")
    public ResponseEntity<List<Vehiculo>> obtenerPorDocumentoComun(@PathVariable Long documentoId) {
        return ResponseEntity.ok(vehiculoService.obtenerPorDocumentoComun(documentoId));
    }

    // 9. REQUERIMIENTO: Buscar vehículos según estado del documento ('Habilitado', 'Vencido', 'En Verificacion')
    @GetMapping("/estado-documento/{estado}")
    public ResponseEntity<List<Vehiculo>> obtenerPorEstadoDocumento(@PathVariable String estado) {
        return ResponseEntity.ok(vehiculoService.obtenerPorEstadoDocumento(estado));
    }

    // 10. Asociar documento adicional a un vehículo existente
    @PostMapping("/{id}/documentos")
    public ResponseEntity<VehiculoDocumento> asociarDocumento(@PathVariable Long id, @RequestBody DocumentoEntradaDTO dto) {
        VehiculoDocumento vd = vehiculoService.asociarDocumento(id, dto);
        return new ResponseEntity<>(vd, HttpStatus.CREATED);
    }

    // 11. POST: Cargar uno o varios documentos PDF en Base64 asociados a un vehículo
    @PostMapping("/{id}/documentos/pdf")
    public ResponseEntity<List<VehiculoDocumento>> cargarDocumentosPdf(@PathVariable Long id, @RequestBody List<DocumentoPdfDTO> listaDtos) {
        List<VehiculoDocumento> resultado = vehiculoService.cargarDocumentosPdf(id, listaDtos);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED);
    }
    
}