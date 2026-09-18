package com.proyecto.vehiculos.dto;

import java.time.LocalDate;

public class DocumentoPdfDTO {
    private Long documentoId;
    private LocalDate fechaExpedicion;
    private LocalDate fechaVencimiento;
    private String estado;
    private String archivoBase64; // Cadena de texto del PDF en Base64

    public DocumentoPdfDTO() {}

    public Long getDocumentoId() { return documentoId; }
    public void setDocumentoId(Long documentoId) { this.documentoId = documentoId; }
    public LocalDate getFechaExpedicion() { return fechaExpedicion; }
    public void setFechaExpedicion(LocalDate fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getArchivoBase64() { return archivoBase64; }
    public void setArchivoBase64(String archivoBase64) { this.archivoBase64 = archivoBase64; }
}