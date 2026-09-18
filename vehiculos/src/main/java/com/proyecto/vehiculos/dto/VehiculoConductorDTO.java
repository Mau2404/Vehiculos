package com.proyecto.vehiculos.dto;

import java.time.LocalDate;

public class VehiculoConductorDTO {
    private Long vehiculoId;
    private Long personaId;
    private LocalDate fechaAsociacion;
    private String estadoConductor; // PO, EA, RO

    public VehiculoConductorDTO() {}

    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }
    public LocalDate getFechaAsociacion() { return fechaAsociacion; }
    public void setFechaAsociacion(LocalDate fechaAsociacion) { this.fechaAsociacion = fechaAsociacion; }
    public String getEstadoConductor() { return estadoConductor; }
    public void setEstadoConductor(String estadoConductor) { this.estadoConductor = estadoConductor; }
}