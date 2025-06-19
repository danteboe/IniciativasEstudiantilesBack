package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.ConcursoRepository;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.IniciativaRepository;


@Service
public class ConcursoIniciativaService 
{
    @Autowired
    private ConcursoRepository concursoRepository;

    @Autowired
    private IniciativaRepository iniciativaRepository;

    private String concursoNoEncontradoString = "Concurso no encontrado";
    private String iniciativaNoEncontradoString = "Iniciativa no encontrado";
    
        /**
     * Asocia una Iniciativa existente a un Concurso
     *
     * @param concursoId   Identificador de la instancia de Concurso
     * @param iniciativaId Identificador de la instancia de Iniciativa
     * @return Instancia de IniciativaEntity que fue asociada a Concurso
     */
    @Transactional
    public IniciativaEntity addIniciativa(Long concursoId, Long iniciativaId) throws EntityNotFoundException {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById(iniciativaId);
        if (iniciativaEntity.isEmpty())
            throw new EntityNotFoundException(iniciativaNoEncontradoString);

        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);
        if (concursoEntity.isEmpty())
            throw new EntityNotFoundException(concursoNoEncontradoString);

        concursoEntity.get().getIniciativas().add(iniciativaEntity.get());
        return iniciativaEntity.get();
    }

    /**
     * Obtiene una colección de instancias de IniciativaEntity asociadas a una instancia de Concurso
     *
     * @param concursoId Identificador de la instancia de Concurso
     * @return Colección de instancias de IniciativaEntity asociadas a la instancia de Concurso
     */
    @Transactional
    public List<IniciativaEntity> getIniciativas(Long concursoId) throws EntityNotFoundException {
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);
        if (concursoEntity.isEmpty())
            throw new EntityNotFoundException(concursoNoEncontradoString);

        return concursoEntity.get().getIniciativas();
    }

    /**
     * Obtiene una instancia de IniciativaEntity asociada a una instancia de Concurso
     *
     * @param concursoId   Identificador de la instancia de Concurso
     * @param iniciativaId Identificador de la instancia de Iniciativa
     * @return La entidad de Iniciativa asociada al Concurso
     */
    @Transactional
    public IniciativaEntity getIniciativa(Long concursoId, Long iniciativaId)
            throws EntityNotFoundException, IllegalOperationException {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById(iniciativaId);
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);

        if (iniciativaEntity.isEmpty())
            throw new EntityNotFoundException(iniciativaNoEncontradoString);

        if (concursoEntity.isEmpty())
            throw new EntityNotFoundException(concursoNoEncontradoString);

        if (!concursoEntity.get().getIniciativas().contains(iniciativaEntity.get()))
            throw new IllegalOperationException("La iniciativa no está asociada al concurso");

        return iniciativaEntity.get();
    }

    /**
     * Reemplaza las instancias de Iniciativa asociadas a una instancia de Concurso
     *
     * @param concursoId Identificador de la instancia de Concurso
     * @param list       Colección de instancias de IniciativaEntity a asociar a la instancia de Concurso
     * @return Nueva colección de IniciativaEntity asociada a la instancia de Concurso
     */
    @Transactional
    public List<IniciativaEntity> replaceIniciativas(Long concursoId, List<IniciativaEntity> list) throws EntityNotFoundException {
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);
        if (concursoEntity.isEmpty())
            throw new EntityNotFoundException(concursoNoEncontradoString);

        concursoEntity.get().getIniciativas().clear();
        for (IniciativaEntity iniciativa : list) {
            Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById(iniciativa.getId());
            if (iniciativaEntity.isEmpty())
                throw new EntityNotFoundException(iniciativaNoEncontradoString);

            concursoEntity.get().getIniciativas().add(iniciativaEntity.get());
        }

        return getIniciativas(concursoId);
    }

    /**
     * Desasocia una Iniciativa existente de un Concurso existente
     *
     * @param concursoId   Identificador de la instancia de Concurso
     * @param iniciativaId Identificador de la instancia de Iniciativa
     */
    @Transactional
    public void removeIniciativa(Long concursoId, Long iniciativaId) throws EntityNotFoundException {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById(iniciativaId);
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);

        if (iniciativaEntity.isEmpty())
            throw new EntityNotFoundException(iniciativaNoEncontradoString);

        if (concursoEntity.isEmpty())
            throw new EntityNotFoundException(concursoNoEncontradoString);

        concursoEntity.get().getIniciativas().remove(iniciativaEntity.get());
    }
        /**
     * Obtiene el número de iniciativas que participan en un concurso
     *
     * @param concursoId Identificador de la instancia de Concurso
     * @return Número de iniciativas participantes en el concurso
     */
    @Transactional
    public int contadorDeIniciativas(Long concursoId) throws EntityNotFoundException {
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);
        if (concursoEntity.isEmpty()) {
            throw new EntityNotFoundException(concursoNoEncontradoString);
        }

        return concursoEntity.get().getIniciativas().size();
    }
}
    


    
