package com.proyecto.vehiculos.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo_vehiculo_aplica", nullable = false, length = 5)
    private String tipoVehiculoAplica;

    @Column(name = "obligatorio_segun_tipo", nullable = false, length = 5)
    private String obligatorioSegunTipo;

    @Column(length = 255)
    private String descripcion;

    public Documento() {
    }

    public Documento(Long id, String codigo, String nombre, String tipoVehiculoAplica, String obligatorioSegunTipo, String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoVehiculoAplica = tipoVehiculoAplica;
        this.obligatorioSegunTipo = obligatorioSegunTipo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipoVehiculoAplica() { return tipoVehiculoAplica; }
    public void setTipoVehiculoAplica(String tipoVehiculoAplica) { this.tipoVehiculoAplica = tipoVehiculoAplica; }

    public String getObligatorioSegunTipo() { return obligatorioSegunTipo; }
    public void setObligatorioSegunTipo(String obligatorioSegunTipo) { this.obligatorioSegunTipo = obligatorioSegunTipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}