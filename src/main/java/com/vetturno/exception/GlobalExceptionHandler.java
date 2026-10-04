package com.vetturno.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.putIfAbsent(error.getField(), error.getDefaultMessage()));

        ApiError body = new ApiError(400, "Datos inválidos", errores);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({RecursoNoEncontradoException.class, ReglaNegocioException.class})
    public ResponseEntity<ApiError> manejarReglaNegocio(RuntimeException ex) {
        ApiError body = new ApiError(400, ex.getMessage(), Map.of());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> manejarCredenciales(BadCredentialsException ex) {
        ApiError body = new ApiError(401, "Credenciales inválidas", Map.of());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarErrorInesperado(Exception ex) {
        ApiError body = new ApiError(500, "Ocurrió un error interno", Map.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
