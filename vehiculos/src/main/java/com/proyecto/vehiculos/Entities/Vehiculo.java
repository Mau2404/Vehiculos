package com.proyecto.vehiculos.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 6)
    private String placa;

    @Column(name = "tipo_vehiculo", nullable = false, length = 20)
    private String tipoVehiculo;

    @Column(name = "tipo_servicio", nullable = false, length = 20)
    private String tipoServicio;

    @Column(name = "tipo_combustible", nullable = false, length = 20)
    private String tipoCombustible;

    @Column(name = "capacidad_pasajeros", nullable = false)
    private Integer capacidadPasajeros;

    @Column(nullable = false, length = 7)
    private String color;

    @Column(nullable = false)
    private Integer modelo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String linea;

    public Vehiculo() {
    }

    public Vehiculo(Long id, String placa, String tipoVehiculo, String tipoServicio, String tipoCombustible, Integer capacidadPasajeros, String color, Integer modelo, String marca, String linea) {
        this.id = id;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.tipoServicio = tipoServicio;
        this.tipoCombustible = tipoCombustible;
        this.capacidadPasajeros = capacidadPasajeros;
        this.color = color;
        this.modelo = modelo;
        this.marca = marca;
        this.linea = linea;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}