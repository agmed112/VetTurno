package com.vetturno.controller;

import com.vetturno.dto.VeterinarioDTO;
import com.vetturno.dto.VeterinarioRequest;
import com.vetturno.service.VeterinarioService;
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
@RequestMapping("/api/veterinarios")
@SecurityRequirement(name = "bearerAuth")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @PostMapping
    public ResponseEntity<VeterinarioDTO> crear(@Valid @RequestBody VeterinarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> listar() {
        return ResponseEntity.ok(veterinarioService.listar());
    }
}
