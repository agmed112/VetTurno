package com.vetturno.service;

import com.vetturno.dto.VeterinarioDTO;
import com.vetturno.dto.VeterinarioRequest;
import com.vetturno.model.Veterinario;
import com.vetturno.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public VeterinarioDTO crear(VeterinarioRequest request) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(request.getNombre());
        veterinario.setEspecialidad(request.getEspecialidad());
        return new VeterinarioDTO(veterinarioRepository.save(veterinario));
    }

    public List<VeterinarioDTO> listar() {
        return veterinarioRepository.findAll().stream()
                .map(VeterinarioDTO::new)
                .toList();
    }
}
