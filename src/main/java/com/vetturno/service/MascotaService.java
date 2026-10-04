package com.vetturno.service;

import com.vetturno.dto.MascotaDTO;
import com.vetturno.dto.MascotaRequest;
import com.vetturno.exception.RecursoNoEncontradoException;
import com.vetturno.model.Mascota;
import com.vetturno.model.Propietario;
import com.vetturno.repository.MascotaRepository;
import com.vetturno.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;

    public MascotaService(MascotaRepository mascotaRepository,
                          PropietarioRepository propietarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    public MascotaDTO crear(MascotaRequest request) {
        Propietario propietario = propietarioRepository.findById(request.getPropietarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El propietario indicado no existe"));

        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setPropietario(propietario);

        return new MascotaDTO(mascotaRepository.save(mascota));
    }

    public List<MascotaDTO> listar() {
        return mascotaRepository.findAll().stream()
                .map(MascotaDTO::new)
                .toList();
    }
}
