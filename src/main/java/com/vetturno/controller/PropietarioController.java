package com.vetturno.controller;

import com.vetturno.dto.PropietarioDTO;
import com.vetturno.dto.PropietarioRequest;
import com.vetturno.service.PropietarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
@SecurityRequirement(name = "bearerAuth")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @PostMapping
    public ResponseEntity<PropietarioDTO> crear(@Valid @RequestBody PropietarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<PropietarioDTO>> listar() {
        return ResponseEntity.ok(propietarioService.listar());
    }
}
