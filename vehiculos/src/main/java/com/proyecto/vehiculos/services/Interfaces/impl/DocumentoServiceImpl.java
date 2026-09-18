package com.proyecto.vehiculos.services.Interfaces.impl;

import com.proyecto.vehiculos.Entities.Documento;
import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoDocumento;
import com.proyecto.vehiculos.dto.DocumentoEntradaDTO;
import com.proyecto.vehiculos.dto.RespuestaVehiculoDTO;
import com.proyecto.vehiculos.repositories.DocumentoRepository;
import com.proyecto.vehiculos.services.Interfaces.IDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoServiceImpl implements IDocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public Documento crearDocumento(Documento doc) {
        validarDocumentoManual(doc);
        if (documentoRepository.existsByCodigo(doc.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un documento parametrizado con el código: " + doc.getCodigo());
        }
        return documentoRepository.save(doc);
    }

    @Override
    public List<Documento> obtenerTodos() {
        return documentoRepository.findAll();
    }

    @Override
    public Documento obtenerPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Documento no encontrado con el ID: " + id));
    }

    @Override
    public Documento actualizarDocumento(Long id, Documento docActualizado) {
        Documento docExistente = obtenerPorId(id);
        validarDocumentoManual(docActualizado);

        docExistente.setNombre(docActualizado.getNombre());
        docExistente.setTipoVehiculoAplica(docActualizado.getTipoVehiculoAplica());
        docExistente.setObligatorioSegunTipo(docActualizado.getObligatorioSegunTipo());
        docExistente.setDescripcion(docActualizado.getDescripcion());

        return documentoRepository.save(docExistente);
    }

    @Override
    public void eliminarDocumento(Long id) {
        Documento doc = obtenerPorId(id);
        documentoRepository.delete(doc);
    }

    // Método Privado para Validación Manual con sentencias IF
    private void validarDocumentoManual(Documento doc) {
        if (doc.getCodigo() == null || doc.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del documento es obligatorio.");
        }
        if (doc.getNombre() == null || doc.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del documento es obligatorio.");
        }
        if (!"A".equals(doc.getTipoVehiculoAplica()) && !"M".equals(doc.getTipoVehiculoAplica()) && !"AM".equals(doc.getTipoVehiculoAplica())) {
            throw new IllegalArgumentException("El campo 'tipoVehiculoAplica' solo permite los valores: 'A' (Automóvil), 'M' (Motocicleta) o 'AM' (Ambos).");
        }
        if (!"RA".equals(doc.getObligatorioSegunTipo()) && !"RM".equals(doc.getObligatorioSegunTipo()) && !"RR".equals(doc.getObligatorioSegunTipo())) {
            throw new IllegalArgumentException("El campo 'obligatorioSegunTipo' solo permite los valores: 'RA', 'RM' o 'RR'.");
        }
    }


}