package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.EventoDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.EventoDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoController 
{
    @Autowired
    private EventoService eventoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventoDetailDTO> findAll() 
    {
        List<EventoEntity> evento = eventoService.getEventos();
        return modelMapper.map(evento, new TypeToken<List<EventoDetailDTO>>(){}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException
    {
        EventoEntity eventoEntity = eventoService.getEvento(id);
        return modelMapper.map(eventoEntity, EventoDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventoDTO create(@RequestBody EventoDTO eventoDTO) throws IllegalOperationException, EntityNotFoundException 
    {
        EventoEntity eventoEntity = eventoService.createEvento(modelMapper.map(eventoDTO, EventoEntity.class));
        return modelMapper.map(eventoEntity, EventoDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoDTO update(@PathVariable("id") Long id, @RequestBody EventoDTO eventoDTO) throws IllegalOperationException, EntityNotFoundException 
    {
        EventoEntity eventoEntity = eventoService.updateEvento(id, modelMapper.map(eventoDTO, EventoEntity.class));
        return modelMapper.map(eventoEntity, EventoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException 
    {
        eventoService.deleteEvento(id);
    }
}
