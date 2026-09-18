package com.proyecto.vehiculos.repositories;

import com.proyecto.vehiculos.Entities.Usuario;
import com.proyecto.vehiculos.Entities.UsuarioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("IUsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioPK> {

    // Consulta JPQL para obtener usuario por clave compuesta (login y persona)
    @Query("SELECT usr FROM UsrANDPer usr WHERE usr.id.login = :login AND usr.id.persona = :persona")
    public abstract Usuario getUsuarioANDPersona(@Param("login") String login, @Param("persona") Long persona);

    // Consulta JPQL para buscar usuario únicamente por el login[cite: 3]
    @Query("SELECT usr FROM UsrANDPer usr WHERE usr.id.login = :login")
    public abstract Usuario findByUsername(@Param("login") String login);
}