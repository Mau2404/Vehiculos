package com.proyecto.vehiculos.services.Interfaces;

import com.proyecto.vehiculos.Entities.Persona;
import com.proyecto.vehiculos.dto.PersonaDTO;

import java.util.List;
import java.util.Map;

public interface IPersonaService {
    Persona crearPersona(PersonaDTO dto);
    List<Persona> obtenerTodas();
    Persona obtenerPorId(Long id);
    Persona actualizarPersona(Long id, PersonaDTO dto);
    void cambiarPassword(String login, String nuevaPassword);
    String regenerarApiKey(String login);
    Map<String, Long> contarPersonasAgrupadasPorTipo();
}