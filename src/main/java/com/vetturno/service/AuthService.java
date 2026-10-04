package com.vetturno.service;

import com.vetturno.dto.AuthResponse;
import com.vetturno.dto.LoginRequest;
import com.vetturno.dto.RegistroRequest;
import com.vetturno.exception.ReglaNegocioException;
import com.vetturno.model.Rol;
import com.vetturno.model.Usuario;
import com.vetturno.repository.UsuarioRepository;
import com.vetturno.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ReglaNegocioException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        // El cliente nunca decide su rol: todo registro público nace como USER.
        usuario.setRol(Rol.USER);

        Usuario guardado = usuarioRepository.save(usuario);
        return new AuthResponse(jwtService.generarToken(guardado.getEmail(), guardado.getRol()));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ReglaNegocioException("Usuario no encontrado"));

        return new AuthResponse(jwtService.generarToken(usuario.getEmail(), usuario.getRol()));
    }
}
