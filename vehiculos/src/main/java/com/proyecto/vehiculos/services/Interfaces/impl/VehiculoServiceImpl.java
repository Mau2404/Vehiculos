package com.proyecto.vehiculos.services.Interfaces.impl;

import com.proyecto.vehiculos.dto.CrearVehiculoDTO;
import com.proyecto.vehiculos.dto.DocumentoEntradaDTO;
import com.proyecto.vehiculos.dto.RespuestaVehiculoDTO;
import com.proyecto.vehiculos.Entities.Documento;
import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoDocumento;
import com.proyecto.vehiculos.repositories.DocumentoRepository;
import com.proyecto.vehiculos.repositories.VehiculoDocumentoRepository;
import com.proyecto.vehiculos.repositories.VehiculoRepository;
import com.proyecto.vehiculos.services.Interfaces.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private VehiculoDocumentoRepository vehiculoDocumentoRepository;

    @Override
    @Transactional
    public RespuestaVehiculoDTO crearVehiculo(CrearVehiculoDTO dto) {
        // 1. Validaciones manuales con IF
        validarVehiculoManual(dto);

        if (vehiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new IllegalArgumentException("Ya existe un vehículo registrado con la placa: " + dto.getPlaca());
        }

        // 2. Mapear y Guardar Vehículo
        Vehiculo v = new Vehiculo();
        v.setPlaca(dto.getPlaca());
        v.setTipoVehiculo(dto.getTipoVehiculo());
        v.setTipoServicio(dto.getTipoServicio());
        v.setTipoCombustible(dto.getTipoCombustible());
        v.setCapacidadPasajeros(dto.getCapacidadPasajeros());
        v.setColor(dto.getColor());
        v.setModelo(dto.getModelo());
        v.setMarca(dto.getMarca());
        v.setLinea(dto.getLinea());

        Vehiculo vehiculoGuardado = vehiculoRepository.save(v);

        // 3. Procesar y guardar los documentos (Estado inicial FORZADO a 'En Verificacion')
        List<VehiculoDocumento> documentosGuardados = new ArrayList<>();

        for (DocumentoEntradaDTO docDto : dto.getDocumentos()) {
            Documento paramDoc = documentoRepository.findById(docDto.getDocumentoId())
                    .orElseThrow(() -> new IllegalArgumentException("El documento con ID " + docDto.getDocumentoId() + " no existe en los parámetros."));

            VehiculoDocumento vd = new VehiculoDocumento();
            vd.setVehiculo(vehiculoGuardado);
            vd.setDocumento(paramDoc);
            vd.setFechaExpedicion(docDto.getFechaExpedicion());
            vd.setFechaVencimiento(docDto.getFechaVencimiento());
            
            // Regla de negocio: Al crear por primera vez, forzar estado "En Verificacion"
            vd.setEstado("En Verificacion");

            documentosGuardados.add(vehiculoDocumentoRepository.save(vd));
        }

        return new RespuestaVehiculoDTO(vehiculoGuardado, documentosGuardados);
    }

    @Override
    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public RespuestaVehiculoDTO obtenerPorId(Long id) {
        Vehiculo v = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con el ID: " + id));
        List<VehiculoDocumento> docs = vehiculoDocumentoRepository.findByVehiculoId(id);
        return new RespuestaVehiculoDTO(v, docs);
    }

    @Override
    public Vehiculo actualizarVehiculo(Long id, Vehiculo vAct) {
        Vehiculo vExist = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));

        vExist.setTipoServicio(vAct.getTipoServicio());
        vExist.setTipoCombustible(vAct.getTipoCombustible());
        vExist.setCapacidadPasajeros(vAct.getCapacidadPasajeros());
        vExist.setColor(vAct.getColor());
        vExist.setModelo(vAct.getModelo());
        vExist.setMarca(vAct.getMarca());
        vExist.setLinea(vAct.getLinea());

        return vehiculoRepository.save(vExist);
    }

    @Override
    public void eliminarVehiculo(Long id) {
        Vehiculo v = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));
        vehiculoRepository.delete(v);
    }

    @Override
    public Vehiculo obtenerPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ningún vehículo registrado con la placa: " + placa));
    }

    @Override
    public List<Vehiculo> obtenerPorTipoVehiculo(String tipoVehiculo) {
        return vehiculoRepository.findByTipoVehiculo(tipoVehiculo);
    }

    @Override
    public List<Vehiculo> obtenerPorDocumentoComun(Long documentoId) {
        return vehiculoRepository.findVehiculosByDocumentoId(documentoId);
    }

    @Override
    public List<Vehiculo> obtenerPorEstadoDocumento(String estado) {
        return vehiculoRepository.findVehiculosByEstadoDocumento(estado);
    }

    @Override
    public VehiculoDocumento asociarDocumento(Long vehiculoId, DocumentoEntradaDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoId));

        Documento paramDoc = documentoRepository.findById(dto.getDocumentoId())
                .orElseThrow(() -> new IllegalArgumentException("Documento parametrizado no existe con ID: " + dto.getDocumentoId()));

        if (dto.getFechaExpedicion() == null || dto.getFechaVencimiento() == null) {
            throw new IllegalArgumentException("Las fechas de expedición y vencimiento son obligatorias.");
        }

        VehiculoDocumento vd = new VehiculoDocumento();
        vd.setVehiculo(vehiculo);
        vd.setDocumento(paramDoc);
        vd.setFechaExpedicion(dto.getFechaExpedicion());
        vd.setFechaVencimiento(dto.getFechaVencimiento());
        
        // Si no envía estado en la asociación posterior, por defecto asigna 'Habilitado'
        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            vd.setEstado("Habilitado");
        } else {
            if (!"Habilitado".equals(dto.getEstado()) && !"Vencido".equals(dto.getEstado()) && !"En Verificacion".equals(dto.getEstado())) {
                throw new IllegalArgumentException("El estado del documento debe ser 'Habilitado', 'Vencido' o 'En Verificacion'.");
            }
            vd.setEstado(dto.getEstado());
        }

        return vehiculoDocumentoRepository.save(vd);
    }

    // Método Privado con la Lógica de Validación Manual mediante IF
    private void validarVehiculoManual(CrearVehiculoDTO dto) {
        // Regla: Obligatorio al menos 1 documento
        if (dto.getDocumentos() == null || dto.getDocumentos().isEmpty()) {
            throw new IllegalArgumentException("El vehículo debe registrarse con al menos 1 documento asociado obligatorio.");
        }

        // Regla: Tipo de Vehículo
        if (!"Automovil".equals(dto.getTipoVehiculo()) && !"Motocicleta".equals(dto.getTipoVehiculo())) {
            throw new IllegalArgumentException("El tipo de vehículo debe ser 'Automovil' o 'Motocicleta'.");
        }

        // Regla: Formato de Placa por Expresión Regular
        if (dto.getPlaca() == null || dto.getPlaca().length() != 6) {
            throw new IllegalArgumentException("La placa debe tener exactamente 6 caracteres.");
        }

        if ("Automovil".equals(dto.getTipoVehiculo())) {
            if (!dto.getPlaca().matches("^[A-Z]{3}[0-9]{3}$")) {
                throw new IllegalArgumentException("Formato de placa inválido para Automóvil. Debe ser 3 letras mayúsculas y 3 números (ej: ABC123).");
            }
        } else if ("Motocicleta".equals(dto.getTipoVehiculo())) {
            if (!dto.getPlaca().matches("^[A-Z]{3}[0-9]{2}[A-Z]{1}$")) {
                throw new IllegalArgumentException("Formato de placa inválido para Motocicleta. Debe ser 3 letras, 2 números y 1 letra (ej: ABC12A).");
            }
        }

        // Regla: Tipo Servicio
        if (!"Publico".equals(dto.getTipoServicio()) && !"Privado".equals(dto.getTipoServicio()) && !"Pu".equals(dto.getTipoServicio()) && !"Pr".equals(dto.getTipoServicio())) {
            throw new IllegalArgumentException("El tipo de servicio debe ser 'Publico', 'Privado', 'Pu' o 'Pr'.");
        }

        // Regla: Tipo Combustible
        if (!"Gasolina".equals(dto.getTipoCombustible()) && !"Gas".equals(dto.getTipoCombustible()) && !"Diesel".equals(dto.getTipoCombustible())) {
            throw new IllegalArgumentException("El tipo de combustible debe ser 'Gasolina', 'Gas' o 'Diesel'.");
        }

        // Regla: Color Hexadecimal
        if (dto.getColor() == null || !dto.getColor().matches("^#([A-Fa-f0-9]{6})$")) {
            throw new IllegalArgumentException("El color debe tener formato Hexadecimal de 6 dígitos que empiece con # (ej: #FF5733).");
        }

        if (dto.getCapacidadPasajeros() == null || dto.getCapacidadPasajeros() < 1) {
            throw new IllegalArgumentException("La capacidad de pasajeros debe ser al menos de 1.");
        }
    }
}