package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.MiembroRepository;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.IniciativaRepository;

@Service
public class IniciativaMiembroService {

    @Autowired
    IniciativaRepository iniciativaRepository;

    @Autowired
    MiembroRepository miembroRepository;

    @Transactional
    public MiembroEntity addMiembroToIniciativa(Long iniciativaId, Long miembroId) throws EntityNotFoundException, IllegalOperationException {
    Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById(iniciativaId);
    Optional<MiembroEntity> miembroEntity = miembroRepository.findById(miembroId);

    if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException("Iniciativa with id not valid");
    if (miembroEntity.isEmpty()) throw new EntityNotFoundException("Miembro with id not valid");

    // Asocia el miembro a la iniciativa
    miembroEntity.get().getIniciativas().add(iniciativaEntity.get());
    iniciativaEntity.get().getMiembros().add(miembroEntity.get());

    // Devuelve el miembro actualizado
    return miembroEntity.get();
}

    @Transactional
    public List<MiembroEntity> getMiembrosFromIniciativa( Long iniciativaId ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );

        if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id not valid" );

        return iniciativaEntity.get().getMiembros();
    }

    @Transactional
    public MiembroEntity getMiembroFromIniciativa( Long iniciativaId, Long miembroId ) throws EntityNotFoundException, IllegalOperationException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );
        Optional<MiembroEntity> miembroEntity = miembroRepository.findById( miembroId );

        if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id not valid" );
        if (miembroEntity.isEmpty()) throw new EntityNotFoundException( "Miembro with id not valid" );

        if (!iniciativaEntity.get().getMiembros().contains(miembroEntity.get())) throw new IllegalOperationException("The member is not associated with the iniciative");
        return miembroEntity.get();
    }

    @Transactional
    public List<MiembroEntity> addMiembrosToIniciativa( Long iniciativaId, List<MiembroEntity> miembros ) throws EntityNotFoundException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );

		if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id not valid" );

        for( MiembroEntity miembro: miembros )
        {
            Optional<MiembroEntity> miembroEntity = miembroRepository.findById( miembro.getId() );
            if (miembroEntity.isEmpty()) throw new EntityNotFoundException( "Miembro with id not valid" );
        }

        iniciativaEntity.get().setMiembros(miembros);
		return iniciativaEntity.get().getMiembros();
    }


    @Transactional
    public void removeMiembroFromIniciativa( Long iniciativaId, Long miembroId ) throws EntityNotFoundException, IllegalOperationException
    {
        Optional<IniciativaEntity> iniciativaEntity = iniciativaRepository.findById( iniciativaId );
        Optional<MiembroEntity> miembroEntity = miembroRepository.findById( miembroId );

        if (iniciativaEntity.isEmpty()) throw new EntityNotFoundException( "Iniciativa with id not valid" );
        if (miembroEntity.isEmpty()) throw new EntityNotFoundException( "Miembro with id not valid" );

        if (!iniciativaEntity.get().getMiembros().contains(miembroEntity.get())) throw new IllegalOperationException("The member is not associated with the iniciative");
        iniciativaEntity.get().getMiembros().remove(miembroEntity.get());
    }

}



