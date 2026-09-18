package com.proyecto.vehiculos.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;

    @Column(name = "tipo_identificacion", nullable = false, length = 5)
    private String tipoIdentificacion; // CC, CE, TI, PP

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(name = "tipo_persona", nullable = false, length = 5)
    private String tipoPersona; // C = Conductor, A = Administrativo

    public Persona() {}

    public Persona(Long id, String identificacion, String tipoIdentificacion, String nombres, String apellidos, String correo, String tipoPersona) {
        this.id = id;
        this.identificacion = identificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.tipoPersona = tipoPersona;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }
}