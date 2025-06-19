package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.EventoRepository;



@Service
public class EventoService {
    @Autowired
    EventoRepository eventoRepository;

    @Transactional
    public EventoEntity createEvento(EventoEntity eventoEntity) throws IllegalOperationException {
        // Evaluar reglas de negocio
        validateEvento(eventoEntity);

        return eventoRepository.save(eventoEntity);
    }

    @Transactional
    public List<EventoEntity> getEventos() {
        return eventoRepository.findAllByOrderByTituloAsc();
    }

    @Transactional
    public EventoEntity getEvento(Long eventoId) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);

        if (!eventoEntity.isPresent())
            throw new EntityNotFoundException("Evento with ID " + eventoId.toString() + " not found");

        return eventoEntity.get();
    }

    @Transactional
    public EventoEntity updateEvento(Long eventoId, EventoEntity newEvento) throws EntityNotFoundException, IllegalOperationException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);

        if (!eventoEntity.isPresent())
            throw new EntityNotFoundException("Evento with ID " + eventoId.toString() + " not found");

        validateEvento(newEvento);

        return eventoRepository.save(newEvento);
    }

    @Transactional
    public void deleteEvento(Long eventoId) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);

        if (!eventoEntity.isPresent())
            throw new EntityNotFoundException("Evento with ID " + eventoId.toString() + " not found");

        eventoRepository.deleteById(eventoId);
    }

    /**
     * Valida las reglas de negocio de un evento
     * 
     * @param evento
     * @throws IllegalOperationException si se incumple alguna regla de negocio
     */
    private void validateEvento(EventoEntity evento) throws IllegalOperationException {
        // regla de negocio: titulo != null
        if (evento.getTitulo() == null)
            throw new IllegalOperationException("Titulo is not valid");

        // regla de negocio: descripcion != null
        if (evento.getDescripcion() == null)
            throw new IllegalOperationException("Descripcion is not valid");

        // regla de negocio: lugar != null
        if (evento.getLugar() == null || evento.getLugar().trim().isEmpty())
            throw new IllegalOperationException("Lugar is not valid");

        // regla de negocio: cupos > 0
        if (evento.getCupos() <= 0)
            throw new IllegalOperationException("Cupos must be greater than 0");

        // regla de negocio: imagenUrl debe ser una URL válida
        if (evento.getImagenPublicitaria()== null ) 
            throw new IllegalOperationException( "Imagen publicitaria is not valid" );
            
        // regla de negocio: duracion > 0
        if (evento.getDuracion() <= 0)
            throw new IllegalOperationException("Duracion must be greater than 0");

        // regla de negocio: fecha <= fecha actual
        Calendar calendar = Calendar.getInstance(); // crea un nuevo calendario
        calendar.setTime(new Date()); // pone la fecha actual en el calendario
        if (evento.getFecha().compareTo(calendar.getTime()) > 0)
            throw new IllegalOperationException("Fecha is not valid");
    }
    
    
}
