package com.vetturno.controller;

import com.vetturno.dto.MascotaDTO;
import com.vetturno.dto.MascotaRequest;
import com.vetturno.service.MascotaService;
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
@RequestMapping("/api/mascotas")
@SecurityRequirement(name = "bearerAuth")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> crear(@Valid @RequestBody MascotaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(mascotaService.listar());
    }
}
