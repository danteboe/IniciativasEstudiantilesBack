package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@DataJpaTest
@Transactional
@Import(ConcursoService.class)
public class ConcursoServiceTest {

    @Autowired
    private ConcursoService concursoService;

    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();
    
    
    private List<ConcursoEntity> concursoList = new ArrayList<>();
    private IniciativaEntity iniciativaEntity;
    private List<IniciativaEntity> iniciativasList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */

    @BeforeEach
    public void setUp()
    {
        clearData();
        insertData();
    }

    /**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() 
    {
        entityManager.getEntityManager().createQuery("delete from IniciativaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ConcursoEntity").executeUpdate();
	}

    private void insertData()
    {
        iniciativaEntity = factory.manufacturePojo(IniciativaEntity.class);
        entityManager.persist(iniciativaEntity); // persistir un evento para las pruebas
        iniciativasList.add(iniciativaEntity);

        // peristir las listas para las pruebas
        for (int i = 0; i < 3; i++) 
        {
            ConcursoEntity concursoEntity = factory.manufacturePojo(ConcursoEntity.class);
            concursoEntity.setIniciativas(iniciativasList);
            concursoEntity.setFecha( generateFechaDespuesHoy() );
            entityManager.persist(concursoEntity);
            concursoList.add(concursoEntity);

		}
	}

    // ============= START TESTS

    @Test
    public void testCreateConcurso() throws IllegalOperationException, EntityNotFoundException {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setFecha( generateFechaDespuesHoy() );
        newEntity.setIniciativas( iniciativasList);
        ConcursoEntity result = concursoService.createConcurso( newEntity );
        assertNotNull(result);
        ConcursoEntity entity = entityManager.find( ConcursoEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getTitulo(), entity.getTitulo());
        assertEquals(newEntity.getImagen(), entity.getImagen());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getPrecio(), entity.getPrecio());
        assertEquals(newEntity.getPremio(), entity.getPremio());
        assertEquals(newEntity.getFecha(), entity.getFecha());
        assertEquals(newEntity.getOrganizado(), entity.getOrganizado());
        assertEquals(newEntity.getCiudad(), entity.getCiudad());
        assertEquals(newEntity.getIniciativas(), entity.getIniciativas());
    }


    @Test
    public void testGetConcursos() {
        List<ConcursoEntity> concursos = concursoService.getConcursos();
        assertEquals(concursoList.size(), concursos.size());

        for (ConcursoEntity concurso : concursos) {
            boolean found = false;
            for (ConcursoEntity entity : concursoList) {
                if (concurso.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
        
    }

    

    @Test
    public void testGetConcursoNotFound() {
        Long concursoId = 100L; // Assuming this ID does not exist in the database
        assertThrows(EntityNotFoundException.class, () -> {
            concursoService.getConcurso(concursoId);
        });
    }


    @Test
    public void testCreateConcursoInvalidTitulo() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setTitulo("");
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });}


    @Test
    public void testCreateConcursoInvalidImagen() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setImagen("");
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }

    @Test
    public void testCreateConcursoInvalidDescripcion() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setDescripcion("");
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }

    @Test
    public void testCreateConcursoInvalidPrecio() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setPrecio(0);
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }

    @Test
    public void testCreateConcursoInvalidPremio() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setPremio("");
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }

    @Test
    public void testCreateConcursoInvalidFecha() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setFecha(new Date());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }

    @Test
    public void testCreateConcursoInvalidOrganizado() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setOrganizado("");
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }

    @Test
    public void testCreateConcursoInvalidciudad() {
        ConcursoEntity newEntity = factory.manufacturePojo(ConcursoEntity.class);
        newEntity.setCiudad("");
        newEntity.setFecha(generateFechaDespuesHoy());
        newEntity.setIniciativas(iniciativasList);
        assertThrows(IllegalOperationException.class, () -> {
            concursoService.createConcurso(newEntity);
        });
    }
    

    @Test
    public void testGetConcurso() throws EntityNotFoundException, IllegalOperationException {
        ConcursoEntity entity = concursoList.get(0);
        ConcursoEntity resultEntity = concursoService.getConcurso(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getTitulo(), resultEntity.getTitulo());
        assertEquals(entity.getImagen(), resultEntity.getImagen());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        assertEquals(entity.getPremio(), resultEntity.getPremio());
        assertEquals(entity.getFecha(), resultEntity.getFecha());
        assertEquals(entity.getOrganizado(), resultEntity.getOrganizado());
        assertEquals(entity.getCiudad(), resultEntity.getCiudad());
        assertEquals(entity.getIniciativas(), resultEntity.getIniciativas());
    }

    @Test
    public void testUpdateConcurso() throws IllegalOperationException, EntityNotFoundException {
        ConcursoEntity entity = concursoList.get(0);
        ConcursoEntity pojoEntity = factory.manufacturePojo(ConcursoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setFecha( generateFechaDespuesHoy() );
        pojoEntity.setIniciativas( iniciativasList);
        concursoService.updateConcurso(entity.getId(), pojoEntity);

        ConcursoEntity resp = entityManager.find(ConcursoEntity.class, entity.getId());

        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getTitulo(), resp.getTitulo());
        assertEquals(pojoEntity.getImagen(), resp.getImagen());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        assertEquals(pojoEntity.getPremio(), resp.getPremio());
        assertEquals(pojoEntity.getFecha(), resp.getFecha());
        assertEquals(pojoEntity.getOrganizado(), resp.getOrganizado());
        assertEquals(pojoEntity.getCiudad(), resp.getCiudad());
        assertEquals(pojoEntity.getIniciativas(), resp.getIniciativas());
    }

    @Test
    public void testDeleteConcurso() throws EntityNotFoundException {
        ConcursoEntity entity = concursoList.get(1);
        concursoService.deleteConcurso(entity.getId());
        ConcursoEntity deleted = entityManager.find(ConcursoEntity.class, entity.getId());
        assertNull(deleted);
    }        
       
    

    // ============= END TESTS
    /* Métodos auxiliares para las pruebas */

    private Date generateFechaDespuesHoy() {
        Calendar calendar = Calendar.getInstance(); // crea un nuevo calendario
        calendar.setTime(new Date()); // pone la fecha actual en el calendario
        calendar.add(Calendar.DAY_OF_MONTH, 1); // suma 1 día a la fecha actual
        return calendar.getTime();
    }

}