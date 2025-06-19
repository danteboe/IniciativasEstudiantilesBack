package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.ComentarioRepository;


@Service
public class ComentarioService 
{
    @Autowired
    ComentarioRepository comentarioRepository;

    @Transactional
    public ComentarioEntity createEntity( ComentarioEntity comentarioEntity ) throws IllegalOperationException
    {
        /*
          Primero verifica que se cumplan las reglas de negocio,
          y después llama a la persistencia
         */
        validateComentario(comentarioEntity);

        /*
         * Aquí se hace el llamado a la persistencia, y la base de datos
         * crea un nuevo ID para el elemento, en este caso, el comentarioEntity
         */
        return comentarioRepository.save( comentarioEntity );
    }

    @Transactional
    public List<ComentarioEntity> getComentarios()
    {
        // Se retorna la totalidad de los comentarios
		return comentarioRepository.findAllByOrderByFechaDesc();
    }

    @Transactional
    public ComentarioEntity getComentario( Long comentarioId ) throws EntityNotFoundException
    {
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById( comentarioId );

        if( comentarioEntity.isEmpty() ) throw new EntityNotFoundException( "Comentario with ID not found" );
        
        return comentarioEntity.get(); 
        
    }

    @Transactional
    public ComentarioEntity updateComentario( Long comentarioId, ComentarioEntity newComentario ) throws EntityNotFoundException, IllegalOperationException
    {
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById( comentarioId );

        if( comentarioEntity.isEmpty() ) throw new EntityNotFoundException( "Comentario with ID not found" );

        validateComentario(newComentario);

        return comentarioRepository.save( newComentario );
    }

    @Transactional
    public void deleteComentario( Long comentarioId ) throws EntityNotFoundException
    {
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById( comentarioId );

        if( comentarioEntity.isEmpty() ) throw new EntityNotFoundException( "Comentario with ID not found" );

        comentarioRepository.deleteById( comentarioId );
    }

    /**
     * Valida un ComentarioEntity de acuerdo a las reglas de negocio
     * @param comentario el comentario a validar
     * @throws IllegalOperationException si no se cumple alguna regla de negocio
     */
    private void validateComentario( ComentarioEntity comentario ) throws IllegalOperationException
    {
        // regla de negocio: contenido!=null
        if( comentario.getContenido() == null ) throw new IllegalOperationException("Contenido is not valid");
 
        // regla de negocio: classification != null
        if( comentario.getCalificacion() == null ) throw new IllegalOperationException("Clasificación is not valid");

        // regla de negocio: evento != null
        if( comentario.getEvento() == null ) throw new IllegalOperationException("Evento is not valid");

        // regla de negocio: fecha != null
        if( comentario.getFecha() == null ) throw new IllegalOperationException("Fecha is not valid");

        // regla de negocio: 0 <= classification <= 5
        if( 0 > comentario.getCalificacion() || comentario.getCalificacion() > 5 ) throw new IllegalOperationException("Clasificación not in range [0,5]");

        // regla de negocio: la fecha debe ser igual o anterior a la actual
        Calendar calendar = Calendar.getInstance(); // crea un nuevo calendario
		calendar.setTime( new Date() ); // pone la fecha actual en el calendario
        if ( comentario.getFecha().compareTo( calendar.getTime() ) >= 0 ) throw new IllegalOperationException("Fecha not in range");
    }

}
