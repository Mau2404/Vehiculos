package com.proyecto.vehiculos.services.Interfaces.impl;

import com.proyecto.vehiculos.dto.PersonaDTO;
import com.proyecto.vehiculos.Entities.Persona;
import com.proyecto.vehiculos.Entities.Usuario;
import com.proyecto.vehiculos.Entities.UsuarioPK;
import com.proyecto.vehiculos.repositories.PersonaRepository;
import com.proyecto.vehiculos.repositories.UsuarioRepository;
import com.proyecto.vehiculos.services.Interfaces.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PersonaServiceImpl implements IPersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Persona crearPersona(PersonaDTO dto) {
        validarPersonaManual(dto);

        if (personaRepository.existsByIdentificacion(dto.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe una persona registrada con la identificación: " + dto.getIdentificacion());
        }

        Persona persona = new Persona();
        persona.setIdentificacion(dto.getIdentificacion());
        persona.setTipoIdentificacion(dto.getTipoIdentificacion());
        persona.setNombres(dto.getNombres());
        persona.setApellidos(dto.getApellidos());
        persona.setCorreo(dto.getCorreo());
        persona.setTipoPersona(dto.getTipoPersona());

        Persona personaGuardada = personaRepository.save(persona);

        // REQUERIMIENTO: Si es tipo ADMINISTRATIVO (A), generar automáticamente su usuario
        if ("A".equalsIgnoreCase(personaGuardada.getTipoPersona())) {
            // Regla de nemotecnia: 1ra letra nombre + 1ra letra apellido + número identificación
            String letraNombre = personaGuardada.getNombres().substring(0, 1).toUpperCase();
            String letraApellido = personaGuardada.getApellidos().substring(0, 1).toUpperCase();
            String loginGenerado = letraNombre + letraApellido + personaGuardada.getIdentificacion();

            // Generación automática de contraseña y APIKey[cite: 4]
            String passwordGenerada = UUID.randomUUID().toString().substring(0, 8);
            String apiKeyGenerada = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

            UsuarioPK pk = new UsuarioPK(loginGenerado, personaGuardada.getId());
            Usuario usuario = new Usuario(pk, passwordGenerada, apiKeyGenerada);

            usuarioRepository.save(usuario);
        }

        return personaGuardada;
    }

    @Override
    public List<Persona> obtenerTodas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona obtenerPorId(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Persona actualizarPersona(Long id, PersonaDTO dto) {
        Persona personaExistente = obtenerPorId(id);
        validarPersonaManual(dto);

        personaExistente.setIdentificacion(dto.getIdentificacion());
        personaExistente.setTipoIdentificacion(dto.getTipoIdentificacion());
        personaExistente.setNombres(dto.getNombres());
        personaExistente.setApellidos(dto.getApellidos());
        personaExistente.setCorreo(dto.getCorreo());
        personaExistente.setTipoPersona(dto.getTipoPersona());

        return personaRepository.save(personaExistente);
    }

    @Override
    @Transactional
    public void cambiarPassword(String login, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByUsername(login);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró un usuario con el login: " + login);
        }
        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
        }
        usuario.setPassword(nuevaPassword);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public String regenerarApiKey(String login) {
        Usuario usuario = usuarioRepository.findByUsername(login);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró un usuario con el login: " + login);
        }
        String nuevaApiKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        usuario.setApikey(nuevaApiKey);
        usuarioRepository.save(usuario);
        return nuevaApiKey;
    }

    @Override
    public Map<String, Long> contarPersonasAgrupadasPorTipo() {
        List<Persona> personas = personaRepository.findAll();
        long conductores = personas.stream().filter(p -> "C".equalsIgnoreCase(p.getTipoPersona())).count();
        long administrativos = personas.stream().filter(p -> "A".equalsIgnoreCase(p.getTipoPersona())).count();
        
        Map<String, Long> resultado = new HashMap<>();
        resultado.put("CONDUCTOR", conductores);
        resultado.put("ADMINISTRATIVO", administrativos);
        return resultado;
    }

    private void validarPersonaManual(PersonaDTO dto) {
        if (dto.getIdentificacion() == null || dto.getIdentificacion().trim().isEmpty())
            throw new IllegalArgumentException("La identificación es obligatoria.");
        if (dto.getNombres() == null || dto.getNombres().trim().isEmpty())
            throw new IllegalArgumentException("Los nombres son obligatorios.");
        if	(dto.getApellidos() == null || dto.getApellidos().trim().isEmpty())
            throw new IllegalArgumentException("Los apellidos son obligatorios.");
        if (dto.getTipoIdentificacion() == null || (!dto.getTipoIdentificacion().matches("^(CC|CE|TI|PP)$")))
            throw new IllegalArgumentException("Tipo de identificación inválido. Valores permitidos: CC, CE, TI, PP.");
        if (dto.getTipoPersona() == null || (!dto.getTipoPersona().matches("^(C|A)$")))
            throw new IllegalArgumentException("Tipo de persona inválido. Valores permitidos: C (Conductor), A (Administrativo).");
    }
}