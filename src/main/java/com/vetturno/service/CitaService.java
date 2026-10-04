package com.vetturno.service;

import com.vetturno.dto.CitaDTO;
import com.vetturno.dto.CitaRequest;
import com.vetturno.exception.RecursoNoEncontradoException;
import com.vetturno.exception.ReglaNegocioException;
import com.vetturno.model.Cita;
import com.vetturno.model.Mascota;
import com.vetturno.model.Veterinario;
import com.vetturno.repository.CitaRepository;
import com.vetturno.repository.MascotaRepository;
import com.vetturno.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaService(CitaRepository citaRepository,
                       MascotaRepository mascotaRepository,
                       VeterinarioRepository veterinarioRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public CitaDTO crear(CitaRequest request) {
        // Las referencias se resuelven en el service porque son una decisión del negocio,
        // no una responsabilidad del controller ni del cliente.
        Mascota mascota = mascotaRepository.findById(request.getMascotaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("La mascota indicada no existe"));

        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El veterinario indicado no existe"));

        // @Future valida la entrada, pero esta comprobación mantiene la regla principal
        // también dentro de la capa de negocio.
        if (!request.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new ReglaNegocioException("La cita debe programarse para una fecha futura");
        }

        // La consulta derivada evita guardar dos citas del mismo veterinario
        // exactamente en la misma fecha y hora.
        boolean horarioOcupado = citaRepository.existsByVeterinarioIdAndFechaHora(
                veterinario.getId(), request.getFechaHora());

        if (horarioOcupado) {
            throw new ReglaNegocioException("El veterinario ya tiene una cita en ese horario");
        }

        Cita cita = new Cita();
        cita.setFechaHora(request.getFechaHora());
        cita.setMotivo(request.getMotivo());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        return new CitaDTO(citaRepository.save(cita));
    }

    public List<CitaDTO> listar() {
        return citaRepository.findAllByOrderByFechaHoraAsc().stream()
                .map(CitaDTO::new)
                .toList();
    }

    public List<CitaDTO> listarPorVeterinario(Long veterinarioId) {
        if (!veterinarioRepository.existsById(veterinarioId)) {
            throw new RecursoNoEncontradoException("El veterinario indicado no existe");
        }

        return citaRepository.findByVeterinarioIdOrderByFechaHoraAsc(veterinarioId).stream()
                .map(CitaDTO::new)
                .toList();
    }
}
