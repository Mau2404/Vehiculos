package com.proyecto.vehiculos.services.Interfaces;

import com.proyecto.vehiculos.Entities.Usuario;
import com.proyecto.vehiculos.Entities.UsuarioPK;
import java.util.List;

public interface IUsuarioService {
    boolean guardar(Usuario usuario);
    boolean actualizar(Usuario usuario);
    boolean eliminar(UsuarioPK id);
    List<Usuario> consultarUsuarios();
    Usuario getUsuarioById(UsuarioPK id);
}