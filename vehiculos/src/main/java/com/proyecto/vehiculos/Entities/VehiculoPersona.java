package com.proyecto.vehiculos.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehiculo_persona")
public class VehiculoPersona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @Column(name = "fecha_asociacion", nullable = false)
    private LocalDate fechaAsociacion;

    @Column(name = "estado_conductor", nullable = false, length = 5)
    private String estadoConductor; // PO = Puede Operar, EA = Espera de Aprobación, RO = Restringido para Operar

    public VehiculoPersona() {}

    public VehiculoPersona(Long id, Vehiculo vehiculo, Persona persona, LocalDate fechaAsociacion, String estadoConductor) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.persona = persona;
        this.fechaAsociacion = fechaAsociacion;
        this.estadoConductor = estadoConductor;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }
    public LocalDate getFechaAsociacion() { return fechaAsociacion; }
    public void setFechaAsociacion(LocalDate fechaAsociacion) { this.fechaAsociacion = fechaAsociacion; }
    public String getEstadoConductor() { return estadoConductor; }
    public void setEstadoConductor(String estadoConductor) { this.estadoConductor = estadoConductor; }
}