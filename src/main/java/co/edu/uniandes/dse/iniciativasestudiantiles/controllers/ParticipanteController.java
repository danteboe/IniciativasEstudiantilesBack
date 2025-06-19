package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.iniciativasestudiantiles.services.ParticipanteService;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ParticipanteDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ParticipanteDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ParticipanteEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.modelmapper.*;



@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
	private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ParticipanteDetailDTO> findAll() {
        List<ParticipanteEntity> participantes = participanteService.getParticipantes();
        return modelMapper.map(participantes, new TypeToken<List<ParticipanteDetailDTO>>(){
        }.getType());
    }

    @GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ParticipanteDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		ParticipanteEntity participanteEntity = participanteService.getParticipante(id);
		return modelMapper.map(participanteEntity, ParticipanteDetailDTO.class);
	}

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParticipanteDTO create(@RequestBody ParticipanteDTO participanteDTO) throws IllegalOperationException, EntityNotFoundException {
        ParticipanteEntity participanteEntity = participanteService.createParticipante(modelMapper.map(participanteDTO, ParticipanteEntity.class));
        return modelMapper.map(participanteEntity, ParticipanteDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ParticipanteDTO update(@PathVariable("id") Long id, @RequestBody ParticipanteDTO participanteDTO) throws IllegalOperationException, EntityNotFoundException {
        ParticipanteEntity participanteEntity = participanteService.updateParticipante(id, modelMapper.map(participanteDTO, ParticipanteEntity.class));
        return modelMapper.map(participanteEntity, ParticipanteDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        participanteService.deleteParticipante(id);
    }

}
