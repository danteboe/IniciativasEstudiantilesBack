package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ParticipanteDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ParticipanteDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ParticipanteEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.EventoParticipanteService;

import java.util.List;


@RestController
@RequestMapping("/eventos")
public class EventoParticipanteController {

    @Autowired
    private EventoParticipanteService EventoParticipanteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{eventoId}/participantes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ParticipanteDetailDTO> getParticipantes(@PathVariable Long eventoId) throws EntityNotFoundException 
    {
        List<ParticipanteEntity> participantes = EventoParticipanteService.getParticipantes(eventoId);
        return modelMapper.map(participantes, new TypeToken<List<ParticipanteDetailDTO>>() {
		}.getType());
    }

    @GetMapping("/{eventoId}/participantes/{participanteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ParticipanteDetailDTO getParticipante(@PathVariable Long eventoId, @PathVariable Long participanteId) throws EntityNotFoundException, IllegalOperationException 
    {
        ParticipanteEntity participante = EventoParticipanteService.getParticipante(eventoId, participanteId);
        return modelMapper.map(participante, ParticipanteDetailDTO.class);
    }

    @PostMapping(value = "/{eventoId}/participantes/{participanteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ParticipanteDetailDTO addParticipante(@PathVariable Long eventoId, @PathVariable Long participanteId) throws EntityNotFoundException {
		ParticipanteEntity participanteEntity = EventoParticipanteService.addParticipante(eventoId, participanteId);
		return modelMapper.map(participanteEntity, ParticipanteDetailDTO.class);
        
	}

    @PutMapping(value = "/{eventoId}/participantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ParticipanteDetailDTO> addParticipantes(@PathVariable Long eventoId, @RequestBody List<ParticipanteDTO> participantes) throws EntityNotFoundException {
		List<ParticipanteEntity> entities = modelMapper.map(participantes, new TypeToken<List<ParticipanteEntity>>() {
		}.getType());

		List<ParticipanteEntity> participantesList = EventoParticipanteService.replaceParticipantes(eventoId, entities);
		return modelMapper.map(participantesList, new TypeToken<List<ParticipanteDetailDTO>>() {
		}.getType());
	}

    @DeleteMapping(value = "/{eventoId}/participantes/{participanteId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeParticipante(@PathVariable Long eventoId, @PathVariable Long participanteId) throws EntityNotFoundException {
		EventoParticipanteService.removeParticipante(eventoId, participanteId);
            }

}
