package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.EventoDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.EventoDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.IniciativaEventoService;
import java.util.List;

@RestController
@RequestMapping("/iniciativas")
public class IniciativaEventoController {

    @Autowired
    private IniciativaEventoService iniciativaEventoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{iniciativaId}/eventos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventoDetailDTO> getEventosByIniciativa(@PathVariable Long iniciativaId) throws EntityNotFoundException 
    {
        List<EventoEntity> eventos = iniciativaEventoService.getEventosFromIniciativa(iniciativaId);
        return modelMapper.map(eventos, new TypeToken<List<EventoDetailDTO>>() {
		}.getType());
    }

    @GetMapping("/{iniciativaId}/eventos/{eventoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoDetailDTO getEventoFromIniciativa(@PathVariable Long iniciativaId, @PathVariable Long eventoId) throws EntityNotFoundException, IllegalOperationException 
    {
        EventoEntity evento = iniciativaEventoService.getEventoFromIniciativa(iniciativaId, eventoId);
        return modelMapper.map(evento, EventoDetailDTO.class);
    }

    @PostMapping("/{iniciativaId}/eventos/{eventoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoDTO addEventoToIniciativa(@PathVariable Long iniciativaId, @PathVariable Long eventoId) throws EntityNotFoundException 
    {
        EventoEntity evento = iniciativaEventoService.addEventoToIniciativa(iniciativaId, eventoId);
        return modelMapper.map(evento, EventoDTO.class );
    }

    @PutMapping(value = "/{iniciativaId}/eventos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventoDetailDTO> replaceEventosFromIniciativa(@PathVariable Long iniciativaId, @RequestBody List<EventoDTO> eventos) throws EntityNotFoundException
    {
        List<EventoEntity> eventosEntity = modelMapper.map(eventos, new TypeToken<List<EventoEntity>>() {
                    }.getType());
        List<EventoEntity> eventosReplaced = iniciativaEventoService.addEventosToIniciativa(iniciativaId, eventosEntity);
        return modelMapper.map(eventosReplaced, new TypeToken<List<EventoDetailDTO>>() {
        }.getType());
    }

    @DeleteMapping("/{iniciativaId}/eventos/{eventoId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeEventoFromIniciativa(@PathVariable Long iniciativaId, @PathVariable Long eventoId) throws EntityNotFoundException 
    {
        iniciativaEventoService.removeEventoFromIniciativa(iniciativaId, eventoId);
    }
}
