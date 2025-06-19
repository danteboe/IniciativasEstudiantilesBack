package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.EventoRepository;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.IniciativaRepository;

@Service
public class IniciativaEventoService 
{

    @Autowired
    IniciativaRepository iniciativaRepository;

    @Autowired
    EventoRepository eventoRepository;

    @Transactional
    public EventoEntity addEventoToIniciativa( Long iniciativaId, Long eventoId ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );
		Optional<EventoEntity> eventoEntity = eventoRepository.findById( eventoId );

		if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id "+ iniciativaId + " not valid" );
		if (eventoEntity.isEmpty()) throw new EntityNotFoundException( "Evento with id "+ eventoId + " not valid" );

        iniciativaEntity.get().getEventos().add( eventoEntity.get() );
        eventoEntity.get().setIniciativa( iniciativaEntity.get() );
		return eventoEntity.get();
    }

    @Transactional
    public List<EventoEntity> getEventosFromIniciativa( Long iniciativaId ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );

		if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id "+ iniciativaId + " not valid" );

        return iniciativaEntity.get().getEventos();
    }

    @Transactional
    public EventoEntity getEventoFromIniciativa( Long iniciativaId, Long eventoId ) throws EntityNotFoundException, IllegalOperationException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );
		Optional<EventoEntity> eventoEntity = eventoRepository.findById( eventoId );

		if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id "+ iniciativaId + " not valid" );
		if (eventoEntity.isEmpty()) throw new EntityNotFoundException( "Evento with id "+ eventoId + " not valid" );

        if (!iniciativaEntity.get().getEventos().contains(eventoEntity.get())) throw new IllegalOperationException("The event is not associated with the iniciative");

        return eventoEntity.get();
    }

    @Transactional
    public List<EventoEntity> addEventosToIniciativa( Long iniciativaId, List<EventoEntity> eventos ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );

		if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id "+ iniciativaId + " not valid" );

        for( EventoEntity evento : eventos )
        {
            Optional<EventoEntity> eventoEntity = eventoRepository.findById( evento.getId() );
            if (eventoEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id "+ evento.getId() + " not valid" );
        }

        iniciativaEntity.get().setEventos(eventos);
		return iniciativaEntity.get().getEventos();
    }

    @Transactional
	public void removeEventoFromIniciativa( Long iniciativaId, Long eventoId ) throws EntityNotFoundException 
    {
		Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );
		Optional<EventoEntity> eventoEntity = eventoRepository.findById( eventoId );

		if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id "+ iniciativaId + " not valid" );
		if (eventoEntity.isEmpty()) throw new EntityNotFoundException( "Evento with id "+ eventoId + " not valid" );

		iniciativaEntity.get().getEventos().remove(eventoEntity.get());
		eventoEntity.get().setIniciativa( null );
	}
}
