package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.IniciativaRepository;

@Service
public class IniciativaService 
{
    
    @Autowired
    IniciativaRepository iniciativaRepository;

    @Transactional
    public IniciativaEntity createIniciativa( IniciativaEntity iniciativaEntity ) throws EntityNotFoundException, IllegalOperationException
    {
        // Evaluar reglas de negocio
        validateIniciativa( iniciativaEntity );

        return iniciativaRepository.save( iniciativaEntity );
    }

    @Transactional
    public List<IniciativaEntity> getIniciativas()
    {
        return iniciativaRepository.findAll();
    }

    @Transactional
    public IniciativaEntity getIniciativa( Long iniciativaId ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciacivaEntity = iniciativaRepository.findById( iniciativaId );

        if( !iniciacivaEntity.isPresent() ) throw new EntityNotFoundException( "Iniciativa with ID " + iniciativaId.toString() + " not found" );
        
        return iniciacivaEntity.get();
    }

    @Transactional
    public IniciativaEntity updateIniciativa( Long iniciativaId, IniciativaEntity newIniciativa ) throws EntityNotFoundException, IllegalOperationException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );

        if( !iniciativaEntity.isPresent() ) throw new EntityNotFoundException( "Iniciativa with ID " + iniciativaId.toString() + " not found" );

        validateIniciativa( newIniciativa );

        return iniciativaRepository.save( newIniciativa );
    }

    @Transactional
    public void deleteIniciativa( Long iniciativaId ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );

        if( !iniciativaEntity.isPresent() ) throw new EntityNotFoundException( "Iniciativa with ID " + iniciativaId.toString() + " not found" );

        iniciativaRepository.deleteById( iniciativaId );
    }

    /**
     * Valida las reglas de negocio de una iniciativa
     * @param iniciativa
     * @throws IllegalOperationException si se incumple alguna regla de negocio
     */
    private void validateIniciativa( IniciativaEntity iniciativa ) throws IllegalOperationException
    {
        // regla de negocio: miembros != null
        if ( iniciativa.getMiembros() == null ) throw new IllegalOperationException( "Miembros is not valid" );

        // regla de negocio: eventos != null
        if ( iniciativa.getEventos() == null ) throw new IllegalOperationException( "Eventos is not valid" );

        // regla de negocio: concursos != null
        if ( iniciativa.getConcursos() == null ) throw new IllegalOperationException( "Concursos is not valid" );

        // regla de negocio: fecha != null
        if ( iniciativa.getFechaCreacion() == null ) throw new IllegalOperationException( "Fecha de creacion is not valid" );

        // regla de negocio: nombre != null
        if ( iniciativa.getNombre() == null ) throw new IllegalOperationException( "Nombre is not valid" );

        // regla de negocio: descripción != null
        if ( iniciativa.getDescripcion() == null ) throw new IllegalOperationException( "Descripcion is not valid" );

        // regla de negocio: administrador != null
        if ( iniciativa.getAdministrador() == null ) throw new IllegalOperationException( "Administrador is not valid" );

        // regla de negocio: logo != null
        if ( iniciativa.getLogo() == null ) throw new IllegalOperationException( "Logo is not valid" );

        // regla de negocio: miembros.length() >= 1
        // if ( iniciativa.getMiembros().size() < 1 ) throw new IllegalOperationException( "Miembros is empty" );

        // regla de negocio: fecha <= fecha actual
        Calendar calendar = Calendar.getInstance(); // crea un nuevo calendario
		calendar.setTime( new Date() ); // pone la fecha actual en el calendario
        if ( iniciativa.getFechaCreacion().compareTo( calendar.getTime() ) > 0 ) throw new IllegalOperationException("Fecha not in range.");
    }

}
