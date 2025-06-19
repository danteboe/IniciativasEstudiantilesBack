
package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.ParticipanteRepository;
import jakarta.transaction.Transactional;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ParticipanteEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.ErrorMessage;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.EventoRepository;

@Service
public class EventoParticipanteService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Transactional
    public ParticipanteEntity getParticipante(Long eventoId, Long participanteId) throws EntityNotFoundException, IllegalOperationException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
        Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participanteId);

        if (eventoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);
        
        if (participanteEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PARTICIPANTE_NOT_FOUND);
        
        if (!eventoEntity.get().getParticipantes().contains(participanteEntity.get()))
            throw new IllegalOperationException("Participante is not associated to the Evento");

        return participanteEntity.get();
    }

    @Transactional
    public List<ParticipanteEntity> getParticipantes(Long eventoId) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);

        if (eventoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

        return eventoEntity.get().getParticipantes();
    }

    @Transactional
    public ParticipanteEntity addParticipante(Long eventoId, Long participanteId) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
        Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participanteId);

        if (eventoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);
        
        if (participanteEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PARTICIPANTE_NOT_FOUND);
        
        EventoEntity evento = eventoEntity.get();
        ParticipanteEntity participante = participanteEntity.get();

        // Add the participante to the evento
        evento.getParticipantes().add(participante);
        // Add the evento to the participante
        participante.getEventos().add(evento);

        // Save the changes to the database
        eventoRepository.save(evento);
        participanteRepository.save(participante);

        return participante;
    }

    @Transactional
    public void removeParticipante(Long eventoId, Long participanteId) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);
        Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participanteId);

        if (eventoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

        if (participanteEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.PARTICIPANTE_NOT_FOUND);

        EventoEntity evento = eventoEntity.get();
        ParticipanteEntity participante = participanteEntity.get();

        // Remove the participante from the evento
        evento.getParticipantes().remove(participante);
        // Remove the evento from the participante
        participante.getEventos().remove(evento);

        // Save the changes to the database
        eventoRepository.save(evento);
        participanteRepository.save(participante);
    }

    @Transactional
    public List<ParticipanteEntity> replaceParticipantes(Long eventoId, List<ParticipanteEntity> participantes) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);

        if (eventoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

        EventoEntity evento = eventoEntity.get();

        // Clear the current participantes
        evento.getParticipantes().clear();

        for (ParticipanteEntity participante : participantes) {
            Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participante.getId());

            if (participanteEntity.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.PARTICIPANTE_NOT_FOUND);

            // Add the participante to the evento
            evento.getParticipantes().add(participanteEntity.get());
            // Add the evento to the participante
            participanteEntity.get().getEventos().add(evento);
        }

        // Save the changes to the database
        eventoRepository.save(evento);

        return evento.getParticipantes();
    }

    @Transactional
    public int countParticipantes(Long eventoId) throws EntityNotFoundException {
        Optional<EventoEntity> eventoEntity = eventoRepository.findById(eventoId);

        if (eventoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.EVENTO_NOT_FOUND);

        return eventoEntity.get().getParticipantes().size();
    }
}
