package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.repositories.ConcursoRepository;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;



import java.util.*;

@Service
public class ConcursoService {
    @Autowired
    ConcursoRepository concursoRepository;

    @Transactional
    public ConcursoEntity createConcurso(ConcursoEntity concursoEntity )throws IllegalOperationException, EntityNotFoundException{
        /**
         * Primero verifica que se cumplan las reglas de negocio,
         * y después llama a la persistencia
         */
         validateConcurso(concursoEntity); 
         /**
         * Aqui se hace el llamado a la persistencia, y la base de datos 
         * crea un nuevo ID para el elemento, en este caso, el concursoEntity
         */
         return concursoRepository.save( concursoEntity );
    }
    

    @Transactional
    public List<ConcursoEntity> getConcursos(){
        return concursoRepository.findAllByOrderByTituloAsc();
    }
    private static final String CONCURSO_NOT_FOUND_MESSAGE = "Concurso with ID ";
    private static final String CONCURSO_NOT_FOUND = "not found";

    @Transactional
    public ConcursoEntity getConcurso(Long concursoId) throws EntityNotFoundException, IllegalOperationException{
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);

        if (!concursoEntity.isPresent())
            throw new EntityNotFoundException(CONCURSO_NOT_FOUND_MESSAGE + CONCURSO_NOT_FOUND);

        return concursoEntity.get();
    }

    @Transactional
    public ConcursoEntity updateConcurso(Long concursoId, ConcursoEntity newConcurso)
            throws EntityNotFoundException, IllegalOperationException {

        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);

        if (!concursoEntity.isPresent())
            throw new EntityNotFoundException(CONCURSO_NOT_FOUND_MESSAGE + CONCURSO_NOT_FOUND);

        validateConcurso(newConcurso);

        return concursoRepository.save(newConcurso);
    }

    @Transactional
    public void deleteConcurso(Long concursoId) throws EntityNotFoundException {
        Optional<ConcursoEntity> concursoEntity = concursoRepository.findById(concursoId);

        if (!concursoEntity.isPresent())
            throw new EntityNotFoundException(CONCURSO_NOT_FOUND_MESSAGE + CONCURSO_NOT_FOUND);

        concursoRepository.deleteById(concursoId);
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
     * Verifica que la fecha no sea nula
     * @param date
     * @return true si la fecha no es nula
     */
    private boolean validDate(Date date) {
        return date != null;
    }

    
    /**
     * Verifica que la fecha dada como parámetro sea posterior a la fecha actual
     * @param date
     * @return true si date es posterior a la fecha actual.
     * @throws IllegalOperationException si la fecha no es posterior a la fecha actual
     */
    private void validFutureDate(Date date) throws IllegalOperationException {
        Date current = new Date();
        if (date.compareTo(current) <= 0) {
            throw new IllegalOperationException("The date must be in the future.");
        }
        
    }

    /**
     * Valida que un String no sea mayor a 200 caracteres
     * @param string
     * @throws IllegalOperationException si el String es mayor a 200 caracteres
     */
    private void validateStringSize(String string) throws IllegalOperationException {
        if (string.length() > 200) {
            throw new IllegalOperationException("String exceeds maximum length of 200 characters.");
        }
    }

    /**
     * Valida que un entero sea mayor a 0
     * @param num
     * @throws IllegalOperationException si el entero no es mayor a 0
     */
    private void validatePositiveInteger(int num) throws IllegalOperationException {
        if (num <= 0) {
            throw new IllegalOperationException("Integer must be greater than 0.");
        }
    }


    /**
     * Valida un ConcursoEntity de acuerdo a las reglas de negocio
     * @param concurso
     * @throws IllegalOperationException si no se cumple alguna regla de negocio
     */
    private void validateConcurso( ConcursoEntity concurso ) throws IllegalOperationException
    {
        // regla de negocio: titulo!=null
        if(!validString(concurso.getTitulo())){
            throw new IllegalOperationException("Titulo is not valid.");
        }

        // regla de negocio: imagen!=null
        if(!validString(concurso.getImagen())){
            throw new IllegalOperationException("Imagen is not valid.");
        }

        // regla de negocio: descripcion!=null
        if(!validString(concurso.getDescripcion())){
            throw new IllegalOperationException("Descripcion is not valid.");
        }
        // regla de negocio: precio!=null
        if(!validInteger(concurso.getPrecio())){
            throw new IllegalOperationException("Precio is not valid.");
        }
        
        // regla de negocio: premio!=null
        if(!validString(concurso.getPremio())){
            throw new IllegalOperationException("Premio is not valid.");
        }
        
        // regla de negocio: fecha!=null
        if(!validDate(concurso.getFecha())){
            throw new IllegalOperationException("Fecha is not valid.");
        }
    

        // regla de negocio: organizador!=null 
        if(!validString(concurso.getOrganizado())){
            throw new IllegalOperationException("Organizado is not valid.");
        }
        
        // regla de negocio: ciudad!=null
        if(!validString(concurso.getCiudad())){
            throw new IllegalOperationException("Ciudad is not valid.");
        }
        
        // regla de negocio: descripcion.len()<=200
        validateStringSize(concurso.getDescripcion());

        // regla de negocio: precio > 0
        validatePositiveInteger(concurso.getPrecio());

        validFutureDate(concurso.getFecha());


    }
    

}



