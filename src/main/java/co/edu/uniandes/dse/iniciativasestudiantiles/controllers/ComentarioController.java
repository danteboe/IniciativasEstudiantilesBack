package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.ComentarioService;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ComentarioDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ComentarioDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;



@RestController
@RequestMapping("/comentarios")
public class ComentarioController 
{
    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus( code = HttpStatus.OK )
    public List<ComentarioDetailDTO> findAll()
    {
        List<ComentarioEntity> comentarios = comentarioService.getComentarios();
        return modelMapper.map( comentarios, new TypeToken<List<ComentarioDetailDTO>>(){
        }.getType() );
    }

    @GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ComentarioDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException 
    {
		ComentarioEntity comentarioEntity = comentarioService.getComentario(id);
		return modelMapper.map(comentarioEntity, ComentarioDetailDTO.class);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ComentarioDTO create(@RequestBody ComentarioDTO comentarioDTO) throws IllegalOperationException, EntityNotFoundException
    {
		ComentarioEntity comentarioEntity = comentarioService.createEntity(modelMapper.map(comentarioDTO, ComentarioEntity.class));
		return modelMapper.map(comentarioEntity, ComentarioDTO.class);
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ComentarioDTO update(@PathVariable("id") Long id, @RequestBody ComentarioDTO comentarioDTO) throws IllegalOperationException, EntityNotFoundException 
    {
		ComentarioEntity authorEntity = comentarioService.updateComentario(id, modelMapper.map(comentarioDTO, ComentarioEntity.class));
		return modelMapper.map(authorEntity, ComentarioDTO.class);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException 
    {
		comentarioService.deleteComentario(id);
	}
    
    
}
