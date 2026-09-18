package com.proyecto.vehiculos.config;

import com.proyecto.vehiculos.config.model.Constants;
import com.proyecto.vehiculos.Entities.Usuario;
import com.proyecto.vehiculos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Permisos públicos para autenticación y servicios públicos de la Entrega 2
                .requestMatchers(Constants.LOGIN_URL).permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Todos los demás endpoints requieren autenticación
                .anyRequest().authenticated())
            .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario appUser = usuarioRepository.findByUsername(username);
            if (appUser == null) {
                throw new UsernameNotFoundException("Usuario no encontrado: " + username);
            }
            return new User(appUser.getId().getLogin(), appUser.getPassword(), new ArrayList<>());
        };
    }
}