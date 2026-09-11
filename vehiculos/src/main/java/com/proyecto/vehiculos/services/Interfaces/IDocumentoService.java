package com.proyecto.vehiculos.services.Interfaces;

import com.proyecto.vehiculos.Entities.Documento;
import java.util.List;

public interface IDocumentoService {
    Documento crearDocumento(Documento documento);
    List<Documento> obtenerTodos();
    Documento obtenerPorId(Long id);
    Documento actualizarDocumento(Long id, Documento documento);
    void eliminarDocumento(Long id);
}