package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import java.util.List;

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

import co.edu.uniandes.dse.iniciativasestudiantiles.services.ConcursoService;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ConcursoDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ConcursoDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;


@RestController
@RequestMapping("/concursos")
public class ConcursoController {
    @Autowired
    private ConcursoService concursoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
	@ResponseStatus(code = HttpStatus.OK)
    public List<ConcursoDetailDTO> findall() {
        List<ConcursoEntity> concursos = concursoService.getConcursos();
        return modelMapper.map(concursos, new TypeToken<List<ConcursoDetailDTO>>() {}.getType());
            //concursos son el origen y new TypeToken<List<ConcursoDetailDTO>>() es el destino
        
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ConcursoDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
    ConcursoEntity concursoEntity = concursoService.getConcurso(id);
    return modelMapper.map(concursoEntity, ConcursoDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ConcursoDTO create(@RequestBody ConcursoDTO concursoDTO) throws IllegalOperationException, EntityNotFoundException {
        ConcursoEntity concursoEntity = concursoService.createConcurso(modelMapper.map(concursoDTO, ConcursoEntity.class));
        return modelMapper.map(concursoEntity, ConcursoDTO.class);
    }
    
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ConcursoDTO update(@PathVariable Long id, @RequestBody ConcursoDTO concursoDTO)
                        throws EntityNotFoundException, IllegalOperationException {
        ConcursoEntity concursoEntity = concursoService.updateConcurso(id, modelMapper.map(concursoDTO, ConcursoEntity.class));
        return modelMapper.map(concursoEntity, ConcursoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        concursoService.deleteConcurso(id);
    }

}
