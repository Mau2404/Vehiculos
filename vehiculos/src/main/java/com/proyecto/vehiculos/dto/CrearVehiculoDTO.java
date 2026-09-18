package com.proyecto.vehiculos.dto;

import java.util.List;

public class CrearVehiculoDTO {
    private String placa;
    private String tipoVehiculo;
    private String tipoServicio;
    private String tipoCombustible;
    private Integer capacidadPasajeros;
    private String color;
    private Integer modelo;
    private String marca;
    private String linea;
    private List<DocumentoEntradaDTO> documentos;

    public CrearVehiculoDTO() {}

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(String tipoCombustible) { this.tipoCombustible = tipoCombustible; }

    public Integer getCapacidadPasajeros() { return capacidadPasajeros; }
    public void setCapacidadPasajeros(Integer capacidadPasajeros) { this.capacidadPasajeros = capacidadPasajeros; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getModelo() { return modelo; }
    public void setModelo(Integer modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getLinea() { return linea; }
    public void setLinea(String linea) { this.linea = linea; }

    public List<DocumentoEntradaDTO> getDocumentos() { return documentos; }
    public void setDocumentos(List<DocumentoEntradaDTO> documentos) { this.documentos = documentos; }
}