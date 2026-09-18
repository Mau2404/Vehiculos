package com.proyecto.vehiculos.services.Interfaces.impl;

import com.proyecto.vehiculos.Entities.Usuario;
import com.proyecto.vehiculos.Entities.UsuarioPK;
import com.proyecto.vehiculos.repositories.UsuarioRepository;
import com.proyecto.vehiculos.services.Interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean guardar(Usuario usuario) {
        try {
            if (usuario == null) return false;
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        try {
            if (usuario == null || usuario.getId() == null) return false;
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            return	false;
        }
    }

    @Override
    public boolean eliminar(UsuarioPK id) {
        try {
            Usuario usuario = usuarioRepository.getUsuarioANDPersona(id.getLogin(), id.getPersona());
            if (usuario != null) {
                usuarioRepository.delete(usuario);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Usuario> consultarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(UsuarioPK id) {
        return usuarioRepository.getUsuarioANDPersona(id.getLogin(), id.getPersona());
    }
}