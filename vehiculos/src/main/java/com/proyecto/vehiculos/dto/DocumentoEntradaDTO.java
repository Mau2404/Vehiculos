package com.proyecto.vehiculos.dto;

import java.time.LocalDate;

public class DocumentoEntradaDTO {
    private Long documentoId;
    private LocalDate fechaExpedicion;
    private LocalDate fechaVencimiento;
    private String estado;

    public DocumentoEntradaDTO() {}

    public Long getDocumentoId() { return documentoId; }
    public void setDocumentoId(Long documentoId) { this.documentoId = documentoId; }

    public LocalDate getFechaExpedicion() { return fechaExpedicion; }
    public void setFechaExpedicion(LocalDate fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}