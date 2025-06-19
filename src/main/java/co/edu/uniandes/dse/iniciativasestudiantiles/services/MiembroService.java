package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.ErrorMessage;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.MiembroRepository;
import jakarta.transaction.Transactional;

import java.util.*;

@Service
public class MiembroService {
    

    @Autowired
    MiembroRepository miembroRepository; 

    @Transactional
    public MiembroEntity createMiembro(MiembroEntity miembroEntity) throws EntityNotFoundException, IllegalOperationException{
       
        validateMiembro(miembroEntity);

        return miembroRepository.save(miembroEntity);
    }

    @Transactional
    public List<MiembroEntity> getMiembros(){
        return miembroRepository.findAll();
    }

    @Transactional
    public MiembroEntity getMiembro (Long id) throws EntityNotFoundException{
        Optional<MiembroEntity> miembroEntity = miembroRepository.findById( id );

        if(miembroEntity.isEmpty() ) throw new EntityNotFoundException( ErrorMessage.MIEMBRO_NOT_FOUND );
        
        return miembroEntity.get(); 
    }

    @Transactional
    public MiembroEntity updateMiembro( Long miembroId, MiembroEntity newMiembro ) throws EntityNotFoundException, IllegalOperationException
    {
        Optional<MiembroEntity> miembroEntity = miembroRepository.findById( miembroId );

        if( !miembroEntity.isPresent() ) throw new EntityNotFoundException( ErrorMessage.MIEMBRO_NOT_FOUND );

        validateMiembro(newMiembro);

        return miembroRepository.save( newMiembro );
    }

    @Transactional
    public void deleteMiembro( Long miembroId ) throws EntityNotFoundException
    {
        Optional<MiembroEntity> miembroEntity = miembroRepository.findById( miembroId );

        if( !miembroEntity.isPresent() ) throw new EntityNotFoundException( ErrorMessage.MIEMBRO_NOT_FOUND );

        miembroRepository.deleteById( miembroId );
    }

    /**
     * Valida que un String no esté vacío
     * @param string
     * @return true si es un String válido
     */
    private boolean validString(String string){
        return !(string==null||string.isEmpty());
    }

    /**
     * Valida que sea un Integer válido
     * @param num
     * @return true si es un Integer válido
     */
    private boolean validInteger(Integer num){
        return !(num==null);
    }

    /**
     * Verifica que la fecha dada como parámetro sea anterior a la fecha actual
     * @param date
     * @return true si date es anterior a la fecha actual.
     */
    private boolean validPastDate(Date date){
        Date current = new Date();
        return (date.compareTo(current)<=0);
    }

    /**
     * 
     * @param miembroEntity 
     * @throws IllegalOperationException si el miembro no cumple con las reglas de negocio
     */
    private void validateMiembro(MiembroEntity miembroEntity) throws IllegalOperationException{
        if(!validString(miembroEntity.getNombre())){
            throw new IllegalOperationException("Nombre is not valid.");
        }

        if(!validString(miembroEntity.getFoto())){
            throw new IllegalOperationException("Foto is not valid.");
        }

        if(!validString(miembroEntity.getCorreoElectronico())){
            throw new IllegalOperationException("CorreoElectronico is not valid.");
        }

        if(miembroEntity.getFechaInscripcion()==null||!validPastDate(miembroEntity.getFechaInscripcion())){
            throw new IllegalOperationException("FechaInscripcion is not valid.");
        }

        if(miembroEntity.getEstado()==null){
            throw new IllegalOperationException("Estado is not valid.");

        }

        if(!validString(miembroEntity.getTipo())){
            throw new IllegalOperationException("Tipo is not valid.");
        }

        if(!validInteger(miembroEntity.getCodigo())){
            throw new IllegalOperationException("Foto is not valid.");
        }
    }
}
