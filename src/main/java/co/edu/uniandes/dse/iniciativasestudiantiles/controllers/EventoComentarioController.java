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

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ComentarioDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.ComentarioDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.EventoComentarioService;

@RestController
@RequestMapping("/eventos")
public class EventoComentarioController {
    
    @Autowired
    private EventoComentarioService eventoComentarioService;

    @Autowired
    private ModelMapper modelMapper;
    
    /**
	 * Guarda un comentario dentro de un evento con la informacion que recibe la
	 * URL. Se devuelve el comentario que se guarda en el evento.
	 *
	 * @param eventoId Identificador de la editorial que se esta actualizando.
	 *                    Este debe ser una cadena de dígitos.
	 * @param comentarioId      Identificador del libro que se desea guardar. Este debe
	 *                    ser una cadena de dígitos.
	 * @return JSON {@link ComentarioDTO} - El libro guardado en la editorial.
	 */
	@PostMapping(value = "/{eventoId}/comentarios/{comentarioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ComentarioDTO addComentario(@PathVariable("eventoId") Long eventoId, @PathVariable("comentarioId") Long comentarioId)
			throws EntityNotFoundException {
		ComentarioEntity comentarioEntity = eventoComentarioService.addComentario(eventoId, comentarioId);
		return modelMapper.map(comentarioEntity, ComentarioDTO.class);
	}

    /**
	 * Busca y devuelve todos los comentarios que existen en el evento.
	 *
	 * @param eventoId Identificador del evento que se esta buscando. Este
	 *                    debe ser una cadena de dígitos.
	 * @return JSONArray {@link ComentarioDetailDTO} - Los comentarios encontrados en el evento
     * . Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping(value = "/{eventoId}/comentarios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ComentarioDetailDTO> getComentarios(@PathVariable("eventoId") Long eventoId) throws EntityNotFoundException {
		List<ComentarioEntity> comentarioList = eventoComentarioService.getComentarios(eventoId);
		return modelMapper.map(comentarioList, new TypeToken<List<ComentarioDetailDTO>>() {
		}.getType());
	}

    /**
	 * Busca el comentario con el id asociado dentro del evento con id asociado.
	 *
	 * @param eventoId Identificador del evento que se esta buscando. Este
	 *                    debe ser una cadena de dígitos.
	 * @param comentarioId      Identificador del comentario que se esta buscando. Este debe
	 *                    ser una cadena de dígitos.
	 * @return JSON {@link ComentarioDetailDTO} - El comentario buscado
	 */
	@GetMapping(value = "/{eventoId}/comentarios/{comentarioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ComentarioDetailDTO getComentario(@PathVariable("eventoId") Long eventoId, @PathVariable("comentarioId") Long comentarioId)
			throws EntityNotFoundException, IllegalOperationException {
		ComentarioEntity comentarioEntity = eventoComentarioService.getComentario(eventoId, comentarioId);
		return modelMapper.map(comentarioEntity, ComentarioDetailDTO.class);
	}

    /**
	 * Remplaza las instancias de Comentario asociadas a una instancia de Evento
	 *
	 * @param eventoId Identificador del evento que se esta remplazando.
	 *                    Este debe ser una cadena de dígitos.
	 * @param comentarios       JSONArray {@link ComentarioDTO} El arreglo de comentarios nuevo para
	 *                    el evento.
	 * @return JSON {@link ComentarioDetailDTO} - El arreglo de comentarios guardado en el evento.
	 */
	@PutMapping(value = "/{eventoId}/comentarios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ComentarioDetailDTO> replaceComentarios(@PathVariable("eventoId") Long eventoId,
			@RequestBody List<ComentarioDetailDTO> comentarios) throws EntityNotFoundException {
		List<ComentarioEntity> comentariosList = modelMapper.map(comentarios, new TypeToken<List<ComentarioEntity>>() {
		}.getType());
		List<ComentarioEntity> result = eventoComentarioService.replaceComentarios(eventoId, comentariosList);
		return modelMapper.map(result, new TypeToken<List<ComentarioDetailDTO>>() {
		}.getType());
	}

	/**
     * Elimina la asociación de un comentario con un evento.
     *
     * @param eventoId Identificador del evento.
     * @param comentarioId    Identificador del comentario.
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @DeleteMapping(value = "/{eventoId}/comentarios/{comentarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComentario(@PathVariable("eventoId") Long eventoId, @PathVariable("comentarioId") Long comentarioId)
            throws EntityNotFoundException, IllegalOperationException {
        eventoComentarioService.removeComentario(eventoId, comentarioId);
    }
}
