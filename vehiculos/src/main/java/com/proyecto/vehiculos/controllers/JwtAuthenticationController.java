package com.proyecto.vehiculos.controllers;

import com.proyecto.vehiculos.Entities.Usuario;
import com.proyecto.vehiculos.config.JWTAuthenticationConfig;
import com.proyecto.vehiculos.config.model.JwtRequest;
import com.proyecto.vehiculos.config.model.JwtResponse;
import com.proyecto.vehiculos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(
            value = "/authenticate",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createAuthenticationToken(
            @RequestHeader(value = "ApiKey", required = false) String apiKeyHeader,
            @RequestBody JwtRequest authenticationRequest) throws Exception {

        // REQUISITO: Validar el APIKey enviado en la cabecera (Header) del servicio de autenticación
        Usuario usuario = usuarioRepository.findByUsername(authenticationRequest.getUsername());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no registrado en el sistema.");
        }

        if (apiKeyHeader == null || !apiKeyHeader.equals(usuario.getApikey())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("APIKey inválida o ausente en los headers.");
        }

        if (!authenticationRequest.getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtAuthenticationConfig.getJWTToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }
}