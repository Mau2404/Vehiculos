package com.proyecto.vehiculos.Entities;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity(name = "UsrANDPer")
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UsuarioPK id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "apikey")
    private String apikey;

    public Usuario() {}

    public Usuario(UsuarioPK id, String password, String apikey) {
        this.id = id;
        this.password = password;
        this.apikey = apikey;
    }

    public UsuarioPK getId() { return id; }
    public void setId(UsuarioPK id) { this.id = id; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getApikey() { return apikey; }
    public void setApikey(String apikey) { this.apikey = apikey; }
}