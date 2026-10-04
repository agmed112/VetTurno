package com.vetturno.service;

import com.vetturno.dto.PropietarioDTO;
import com.vetturno.dto.PropietarioRequest;
import com.vetturno.model.Propietario;
import com.vetturno.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public PropietarioDTO crear(PropietarioRequest request) {
        Propietario propietario = new Propietario();
        propietario.setNombre(request.getNombre());
        propietario.setTelefono(request.getTelefono());
        propietario.setEmail(request.getEmail());
        return new PropietarioDTO(propietarioRepository.save(propietario));
    }

    public List<PropietarioDTO> listar() {
        return propietarioRepository.findAll().stream()
                .map(PropietarioDTO::new)
                .toList();
    }
}
