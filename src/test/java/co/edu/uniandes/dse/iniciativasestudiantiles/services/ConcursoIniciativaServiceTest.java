package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import( ConcursoIniciativaService.class )
public class ConcursoIniciativaServiceTest {

    @Autowired
    private ConcursoIniciativaService concursoIniciativaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<IniciativaEntity> iniciativaList = new ArrayList<>();
    private List<ConcursoEntity> concursoList = new ArrayList<>();
    private String concursoNoEncontradoString = "Concurso no encontrado";

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from IniciativaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ConcursoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        List<IniciativaEntity> iniciativas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
            entityManager.persist(iniciativa);
            iniciativas.add(iniciativa);
        }
        iniciativaList.addAll(iniciativas);
    
        ConcursoEntity concurso = factory.manufacturePojo(ConcursoEntity.class);
        concurso.setIniciativas(iniciativas);
        entityManager.persist(concurso);
        concursoList.add(concurso);
    }
    

    // TESTS
    @Test
    public void testAddIniciativa() throws Exception {
    ConcursoEntity concurso = concursoList.get(0);
    IniciativaEntity nuevaIniciativa = factory.manufacturePojo(IniciativaEntity.class);
    entityManager.persist(nuevaIniciativa); 
    IniciativaEntity resultado = concursoIniciativaService.addIniciativa(concurso.getId(), nuevaIniciativa.getId());

    // Verificar que la iniciativa se ha añadido correctamente al concurso
    assertNotNull(resultado);
    assertEquals(nuevaIniciativa.getId(), resultado.getId());

    List<IniciativaEntity> iniciativas = concursoIniciativaService.getIniciativas(concurso.getId());
    assertTrue(iniciativas.contains(nuevaIniciativa)); 
    }


    @Test
    public void testGetIniciativas() throws EntityNotFoundException {
    Long concursoId = concursoList.get(0).getId();
    List<IniciativaEntity> iniciativas = concursoIniciativaService.getIniciativas(concursoId);
    // Verificar que la lista no esté vacía y que contenga las iniciativas asociadas
    assertNotNull(iniciativas);
    assertFalse(iniciativas.isEmpty());
    for (IniciativaEntity iniciativa : iniciativas) {
        assertTrue(iniciativaList.contains(iniciativa));
        }
    }

    @Test
    public void testGetIniciativas_NoExisteConcurso() throws EntityNotFoundException {
    Long concursoIdInexistente = Long.MAX_VALUE;
    // Verificar que se lanza una excepción al intentar obtener iniciativas de un concurso inexistente
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        concursoIniciativaService.getIniciativas(concursoIdInexistente);
    });
    assertEquals(concursoNoEncontradoString, exception.getMessage());
}



    @Test
    public void testGetIniciativa() throws EntityNotFoundException, IllegalOperationException {
    
    Long concursoId = concursoList.get(0).getId();
    Long iniciativaId = iniciativaList.get(0).getId();
    IniciativaEntity iniciativa = concursoIniciativaService.getIniciativa(concursoId, iniciativaId);

    // Verificar que la iniciativa no sea nula y que tenga el ID correcto
    assertNotNull(iniciativa);
    assertEquals(iniciativaId, iniciativa.getId());
    }

    @Test
    public void testReplaceIniciativas() throws EntityNotFoundException {
        Long concursoId = concursoList.get(0).getId();
        List<IniciativaEntity> nuevasIniciativas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
            entityManager.persist(iniciativa);
            nuevasIniciativas.add(iniciativa);
        }
    
        List<IniciativaEntity> result = concursoIniciativaService.replaceIniciativas(concursoId, nuevasIniciativas);
    
        assertEquals(nuevasIniciativas.size(), result.size());
        for (IniciativaEntity iniciativa : nuevasIniciativas) {
            assertTrue(result.contains(iniciativa));
        }
    }

    @Test
    public void testReplaceIniciativas_NoExisteConcurso() {
    Long concursoIdInexistente = Long.MAX_VALUE;

    List<IniciativaEntity> nuevasIniciativas = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
        IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
        entityManager.persist(iniciativa);
        nuevasIniciativas.add(iniciativa);
    }

    // Verificar que se lanza una excepción al intentar reemplazar iniciativas en un concurso inexistente
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        concursoIniciativaService.replaceIniciativas(concursoIdInexistente, nuevasIniciativas);
    });
    assertEquals(concursoNoEncontradoString , exception.getMessage());
}


    @Test
    public void testRemoveIniciativa() throws EntityNotFoundException {
    Long concursoId = concursoList.get(0).getId();
    Long iniciativaId = iniciativaList.get(0).getId();

    concursoIniciativaService.removeIniciativa(concursoId, iniciativaId);

    // Verificar que la iniciativa ya no está asociada al concurso
    List<IniciativaEntity> iniciativas = concursoIniciativaService.getIniciativas(concursoId);
    assertFalse(iniciativas.stream().anyMatch(i -> i.getId().equals(iniciativaId)));
    }


    @Test
    public void testRemoveIniciativa_NoExisteConcurso() {
    Long concursoIdInexistente = Long.MAX_VALUE;
    Long iniciativaId = iniciativaList.get(0).getId(); // Usar un ID de iniciativa válido para el caso

    // Verificar que se lanza una excepción al intentar eliminar una iniciativa de un concurso inexistente
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        concursoIniciativaService.removeIniciativa(concursoIdInexistente, iniciativaId);
    });
    assertEquals(concursoNoEncontradoString , exception.getMessage());
}

    @Test
    public void testContadorDeIniciativas() throws EntityNotFoundException {
    Long concursoId = concursoList.get(0).getId();
    // Contar el número de iniciativas asociadas al concurso
    int contador = concursoIniciativaService.contadorDeIniciativas(concursoId);

    assertEquals(iniciativaList.size(), contador);
    }

    @Test
    public void testContadorDeIniciativas_NoExisteConcurso() {
    Long concursoIdInexistente = Long.MAX_VALUE;

    // Verificar que se lanza una excepción al intentar contar iniciativas de un concurso inexistente
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        concursoIniciativaService.contadorDeIniciativas(concursoIdInexistente);
    });
    assertEquals(concursoNoEncontradoString, exception.getMessage());
    }

}
