package com.proyecto.vehiculos.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehiculo_documentos")
public class VehiculoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    @Column(name = "fecha_expedicion", nullable = false)
    private LocalDate fechaExpedicion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 20)
    private String estado;

    @Lob
    @Column(name = "archivo_pdf", columnDefinition = "LONGBLOB")
    private byte[] archivoPdf;

    public VehiculoDocumento() {}

    public VehiculoDocumento(Long id, Vehiculo vehiculo, Documento documento, LocalDate fechaExpedicion, LocalDate fechaVencimiento, String estado, byte[] archivoPdf) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.documento = documento;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.archivoPdf = archivoPdf;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public Documento getDocumento() { return documento; }
    public void setDocumento(Documento documento) { this.documento = documento; }

    public LocalDate getFechaExpedicion() { return fechaExpedicion; }
    public void setFechaExpedicion(LocalDate fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public byte[] getArchivoPdf() { return archivoPdf; }
    public void setArchivoPdf(byte[] archivoPdf) { this.archivoPdf = archivoPdf; }
}