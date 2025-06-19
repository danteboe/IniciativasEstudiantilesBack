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

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.IniciativaDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.IniciativaDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.IniciativaService;

@RestController
@RequestMapping("/iniciativas")
public class IniciativaController 
{
    @Autowired
    private IniciativaService iniciativaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<IniciativaDetailDTO> findAll() 
    {
        List<IniciativaEntity> iniciativas = iniciativaService.getIniciativas();
        return modelMapper.map(iniciativas, new TypeToken<List<IniciativaDetailDTO>>(){
        }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public IniciativaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException
    {
        IniciativaEntity iniciativaEntity = iniciativaService.getIniciativa(id);
        return modelMapper.map(iniciativaEntity, IniciativaDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public IniciativaDTO create(@RequestBody IniciativaDTO iniciativaDTO) throws IllegalOperationException, EntityNotFoundException 
    {
        IniciativaEntity iniciativaEntity = iniciativaService.createIniciativa(modelMapper.map(iniciativaDTO, IniciativaEntity.class));
        return modelMapper.map(iniciativaEntity, IniciativaDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public IniciativaDTO update(@PathVariable("id") Long id, @RequestBody IniciativaDTO iniciativaDTO) throws IllegalOperationException, EntityNotFoundException 
    {
        IniciativaEntity iniciativaEntity = iniciativaService.updateIniciativa(id, modelMapper.map(iniciativaDTO, IniciativaEntity.class));
        return modelMapper.map(iniciativaEntity, IniciativaDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException 
    {
        iniciativaService.deleteIniciativa(id);
    }
}
