package com.proyecto.vehiculos.controllers;

import com.proyecto.vehiculos.dto.PasswordUpdateDTO;
import com.proyecto.vehiculos.dto.PersonaDTO;
import com.proyecto.vehiculos.Entities.Persona;
import com.proyecto.vehiculos.services.Interfaces.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    // 1. POST: Crear Persona (Genera usuario automático si es Administrativo con nemotecnia, pass y apikey)
    @PostMapping("/personas")
    public ResponseEntity<Persona> crearPersona(@RequestBody PersonaDTO dto) {
        Persona nuevaPersona = personaService.crearPersona(dto);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    // 2. GET: Listar todas las personas
    @GetMapping("/personas")
    public ResponseEntity<List<Persona>> obtenerTodas() {
        return ResponseEntity.ok(personaService.obtenerTodas());
    }

    // 3. GET: Obtener persona por ID
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(personaService.obtenerPorId(id));
    }

    // 4. PUT: Actualizar datos de una persona
    @PutMapping("/personas/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody PersonaDTO dto) {
        Persona personaActualizada = personaService.actualizarPersona(id, dto);
        return ResponseEntity.ok(personaActualizada);
    }

    // 5. PUT: Cambiar contraseña de usuario específico (login por URL, nueva password por Body)
    @PutMapping("/usuarios/{login}/password")
    public ResponseEntity<Map<String, String>> cambiarPassword(@PathVariable String login, @RequestBody PasswordUpdateDTO dto) {
        personaService.cambiarPassword(login, dto.getPassword());
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada exitosamente para el usuario: " + login));
    }

    // 6. GET: Regenerar APIKey de un usuario específico por login
    @GetMapping("/usuarios/{login}/apikey")
    public ResponseEntity<Map<String, String>> regenerarApiKey(@PathVariable String login) {
        String nuevaApiKey = personaService.regenerarApiKey(login);
        return ResponseEntity.ok(Map.of("login", login, "nuevaApiKey", nuevaApiKey));
    }

    // 7. GET (Público/Informativo): Total de personas agrupadas por tipo
    @GetMapping("/public/personas/agrupadas")
    public ResponseEntity<Map<String, Long>> contarPersonasAgrupadas() {
        return ResponseEntity.ok(personaService.contarPersonasAgrupadasPorTipo());
    }
}