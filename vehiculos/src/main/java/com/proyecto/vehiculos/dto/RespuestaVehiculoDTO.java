package com.proyecto.vehiculos.dto;

import com.proyecto.vehiculos.Entities.Vehiculo;
import com.proyecto.vehiculos.Entities.VehiculoDocumento;
import java.util.List;

public class RespuestaVehiculoDTO {
    private Vehiculo vehiculo;
    private List<VehiculoDocumento> documentosAsociados;

    public RespuestaVehiculoDTO(Vehiculo vehiculo, List<VehiculoDocumento> documentosAsociados) {
        this.vehiculo = vehiculo;
        this.documentosAsociados = documentosAsociados;
    }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public List<VehiculoDocumento> getDocumentosAsociados() { return documentosAsociados; }
    public void setDocumentosAsociados(List<VehiculoDocumento> documentosAsociados) { this.documentosAsociados = documentosAsociados; }
}