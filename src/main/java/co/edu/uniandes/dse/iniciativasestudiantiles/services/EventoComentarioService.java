package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.ErrorMessage;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.ComentarioRepository;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.EventoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventoComentarioService {
    
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    /**
     * 
     * @param eventoId Identificador de la instancia de Evento
     * @param comentarioId Identificador de la instancia de Comentario
     * @return Instancia de ComentarioEntity que fue asociado al Evento
     * @throws EntityNotFoundException
     */
    @Transactional
	public ComentarioEntity addComentario(Long eventoId, Long comentarioId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un comentario al evento con id = {0}", eventoId);
		Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
		Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);

		if (eventoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

		if (comentarioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

		comentarioEntity.get().setEvento(eventoEntity.get());
		log.info("Termina proceso de asociarle un comentario al evento con id = {0}", eventoId);
		return comentarioEntity.get();
	}

    /**
     * Obtiene una colección de instancias de ComentarioEntity asociadas a una instancia
     * de Evento
     * @param eventoId identificador de la instancia de Evento
     * @return Colección de instancias de ComentarioEntity asociadas a la instancia de 
     *         Evento
     * @throws EntityNotFoundException
     */
    @Transactional
	public List<ComentarioEntity> getComentarios(Long eventoId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los comentarios del evento con id = {0}", eventoId);
		Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
		if (eventoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

		log.info("Termina proceso de consultar todos los comentarios del evento con id = {0}", eventoId);
		return eventoEntity.get().getComentarios();
	}

    /**
     * 
     * @param eventoId Identificador de la instancia de Evento
     * @param comentarioId Identificador de la instancia de Comentario
     * @return La entidad de Comentario del Evento
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @Transactional
	public ComentarioEntity getComentario(Long eventoId, Long comentarioId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el comentario con id = "+ comentarioId+ " del evento con id = " + eventoId);
		Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
		Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);

		if (eventoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

		if (comentarioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

		log.info("Termina proceso de consultar el comentario con id = " +comentarioId+" del evento con id = " + eventoId);
		
        if(!eventoEntity.get().getComentarios().contains(comentarioEntity.get()))
        throw new IllegalOperationException("The comment is not associated to the event");

		return comentarioEntity.get();
	}

    /**
	 * Remplaza las instancias de Comentario asociadas a una instancia de Evento
	 *
	 * @param eventoId Identificador de la instancia de Evento
	 * @param comentarios  Colección de instancias de ComentarioEntity a asociar a instancia
	 *                 de Evento
	 * @return Nueva colección de ComentarioEntity asociada a la instancia de Evento
	 */
    @Transactional
	public List<ComentarioEntity> replaceComentarios(Long eventoId, List<ComentarioEntity> comentarios) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los comentarios asociados al evento con id = {0}", eventoId);
		Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
		if (eventoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

		for (ComentarioEntity comentario : comentarios) {
			Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentario.getId());
			if (comentarioEntity.isEmpty())
				throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

				comentarioEntity.get().setEvento(eventoEntity.get());
		}
		log.info("Finaliza proceso de reemplazar los comentarios asociados al evento con id = {0}", eventoId);
		eventoEntity.get().setComentarios(comentarios);
		return eventoEntity.get().getComentarios();
	}

    /**
	 * Desasocia un Comentario existente de un Evento existente
	 *
	 * @param eventoId Identificador de la instancia de Evento
	 * @param comentarioId   Identificador de la instancia de comentario
	 */
	@Transactional
	public void removeComentario(Long eventoId, Long comentarioId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un comentario del evento con id = {0}", eventoId);
		Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
		if (eventoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

		Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
		if (comentarioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

		comentarioEntity.get().setEvento(null);
		eventoEntity.get().getComentarios().remove(comentarioEntity.get());
		log.info("Finaliza proceso de borrar un comentario del evento con id = {0}", eventoId);
	}

}
