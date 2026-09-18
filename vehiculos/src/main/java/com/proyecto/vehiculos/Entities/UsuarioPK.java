package com.proyecto.vehiculos.Entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "login")
    private String login;

    @Column(name = "persona")
    private Long persona;

    public UsuarioPK() {}

    public UsuarioPK(String login, Long persona) {
        this.login = login;
        this.persona = persona;
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public Long getPersona() { return persona; }
    public void setPersona(Long persona) { this.persona = persona; }

    // METODOS OBLIGATORIOS PARA CLASES ID COMPUESTAS EN JPA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPK usuarioPK = (UsuarioPK) o;
        return Objects.equals(login, usuarioPK.login) && Objects.equals(persona, usuarioPK.persona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, persona);
    }
}