package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.ParticipanteRepository;
import jakarta.transaction.Transactional;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ParticipanteEntity;

import java.util.*;


@Service
public class ParticipanteService {

    @Autowired
    ParticipanteRepository participanteRepository;

    @Transactional
    public ParticipanteEntity createParticipante (ParticipanteEntity participanteEntity) throws EntityNotFoundException, IllegalOperationException {
        validateParticipante(participanteEntity);
        return participanteRepository.save(participanteEntity);
    }

    @Transactional
    public List<ParticipanteEntity> getParticipantes() {
        return participanteRepository.findAll();
    }

    @Transactional
    public ParticipanteEntity getParticipante(Long participanteId) throws EntityNotFoundException {
        Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participanteId);

        if (!participanteEntity.isPresent()) throw new EntityNotFoundException("Participante with the given id was not found");
        return participanteEntity.get();
    }

    @Transactional
    public ParticipanteEntity updateParticipante (Long participanteId, ParticipanteEntity newParticipante) throws EntityNotFoundException, IllegalOperationException {
        Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participanteId);

        if( !participanteEntity.isPresent() ) throw new EntityNotFoundException( "Participante with the given id was not found");
        validateParticipante(newParticipante);

        return participanteRepository.save( newParticipante );
    }

    @Transactional
    public void deleteParticipante( Long participanteId ) throws EntityNotFoundException {
        Optional<ParticipanteEntity> participanteEntity = participanteRepository.findById(participanteId);

        if( !participanteEntity.isPresent() ) throw new EntityNotFoundException("Participante with the given id was not found");
        participanteRepository.deleteById(participanteId);
    }

    /** Validacion de relgas de negocio
     * @param participanteEntity
     * @throws IllegalOperationException si las reglas de negocio no son cumplidas por el participante
    */

    private void validateParticipante (ParticipanteEntity participanteEntity) throws IllegalOperationException{
        if(participanteEntity.getNombre() == null)
            throw new IllegalOperationException("Nombre is not valid.");

        if(participanteEntity.getFoto() == null)
            throw new IllegalOperationException("Foto is not valid");

        if(participanteEntity.getCorreoElectronico() == null)
            throw new IllegalOperationException("Correo is not valid.");

        if(Integer.toString(participanteEntity.getCedula()).length() > 10 || Integer.toString(participanteEntity.getCedula()).length() < 8 || participanteEntity.getCedula() == null)
            throw new IllegalOperationException("Cédula is not valid.");
        
        if(participanteEntity.getGenero() == null)
            throw new IllegalOperationException("Genero is not valid.");

        if(participanteEntity.getCarrera() == null)
            throw new IllegalOperationException("Carrera is not valid.");

    }
}
