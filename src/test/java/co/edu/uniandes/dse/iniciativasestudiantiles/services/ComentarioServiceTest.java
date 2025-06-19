package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith( SpringExtension.class )
@DataJpaTest
@Transactional
@Import( ComentarioService.class )
public class ComentarioServiceTest 
{

    /**
     * La inyección de dependencias, que se hace con Autowired, es
     * cuando el framework "inserta" nuevas instancias. En otras
     * palabras, evita que la variable sea null sin necesidad de 
     * hacerlo manualmente. En este caso, el SpringBoot (que 
     * utiliza las convenciones de JEE).
     */
    @Autowired
    private ComentarioService comentarioService;

    /**
     * TestEntityManager sirve para chequear las bases de datos
     * sin necesidad de hacerlo manualmente
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * A diferencia de SpringBoot, Podam es una fabrica que sí crea
     * datos específicos 
     */
    private final PodamFactory factory = new PodamFactoryImpl();

    // como todos los comentarios tienen evento -> se inicializa un evento
    private EventoEntity eventoEntity;

    private final List<ComentarioEntity> comentarioList = new ArrayList<>();

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
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData()
    {
		eventoEntity = factory.manufacturePojo(EventoEntity.class);
		entityManager.persist(eventoEntity); // persistir un evento para las pruebas

        // persist una lista de comentarios para las pruebas
        for (int i = 0; i < 3; i++) {
			ComentarioEntity comentarioEntity = factory.manufacturePojo(ComentarioEntity.class);
			comentarioEntity.setEvento( eventoEntity );
            comentarioEntity.setCalificacion( generateClasificacion() );
            comentarioEntity.setFecha( generateFecha() );
			entityManager.persist( comentarioEntity );
            comentarioList.add(comentarioEntity);
		}
	}


    // ============= START TESTS

    /**
     * Test de la creación de un comentario
     * @throws IllegalOperationException si no se cumple alguna regla de negocio
     */
    @Test
    public void testCreateComentario() throws IllegalOperationException
    {
        // crear una nueva entidad con datos randomizados por Podam
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setEvento( eventoEntity ); // añadir el evento creado en el BeforeEach

        newEntity.setCalificacion( generateClasificacion() ); // pone una clasificacion valida

		newEntity.setFecha( generateFecha() ); // pone una fecha valida


        // crea el nuevo entity creado por podam en la base de datos,
        // y el resultado es el nuevo entity
        ComentarioEntity result = comentarioService.createEntity( newEntity );
        assertNotNull(result);

        // Obtener la entidad de la base de datos usando el entityManager
        ComentarioEntity entity = entityManager.find( ComentarioEntity.class, result.getId());

        // Una vez se crea la entidad, se verifica que se haya creado adecuadamente
        // en la base de datos
        assertEquals( newEntity.getId(), entity.getId() );
        assertEquals( newEntity.getCalificacion(), entity.getCalificacion() );
        assertEquals( newEntity.getContenido(), entity.getContenido() );
        assertEquals( newEntity.getEvento(), entity.getEvento() );
        assertEquals( newEntity.getFecha(), entity.getFecha() );
        assertEquals( newEntity.getFoto(), entity.getFoto() );
    }

    @Test
    public void testCreateInvalidComentario()
    {
        assertThrows(IllegalOperationException.class, () ->
        {
            // crear una nueva entidad con datos randomizados por Podam
            ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
            comentarioService.createEntity(newEntity);
        });

    }


    /**
     * test del get comentarios
     */
    @Test
    public void testGetComentarios()
    {
        List<ComentarioEntity> list = comentarioService.getComentarios();
        assertEquals( comentarioList.size(), list.size() );

        for( ComentarioEntity entity : list )
        {
            boolean found = false;
            for (ComentarioEntity storedEntity : comentarioList) 
            {
				if ( entity.getId().equals(storedEntity.getId()) ) found = true;
			}
			assertTrue( found );
        }
    }

    /**
     * test del get comentario
     * @throws EntityNotFoundException si no se encuentra el comentario 
     */
    @Test
	public void testGetComentario() throws EntityNotFoundException  
    {
		ComentarioEntity entity = comentarioList.getFirst();
		ComentarioEntity resultEntity = comentarioService.getComentario( entity.getId() );
		assertNotNull( resultEntity ); 
		assertEquals( entity.getId(), resultEntity.getId() );
        assertEquals( entity.getCalificacion(), resultEntity.getCalificacion() );
        assertEquals( entity.getContenido(), resultEntity.getContenido() );
        assertEquals( entity.getEvento(), resultEntity.getEvento() );
        assertEquals( entity.getFecha(), resultEntity.getFecha() );
        assertEquals( entity.getFoto(), resultEntity.getFoto() );
	}

    @Test
    public void testGetInvalidComentario() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, () -> 
        {
            comentarioService.getComentario(0L);
        });
    }

    /**
     * test update comentario
     * @throws EntityNotFoundException si no se encuentra el comentario
     * @throws IllegalOperationException si no se cumplen las reglas de negocio
     * @throws co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException 
     */
    @Test
    public void testUpdateComentario() throws IllegalOperationException, EntityNotFoundException
    {
        ComentarioEntity entity = comentarioList.get(0);
		ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);
		pojoEntity.setId( entity.getId() );
        pojoEntity.setEvento( eventoEntity );
        pojoEntity.setCalificacion( generateClasificacion() );
        pojoEntity.setFecha( generateFecha() );
		comentarioService.updateComentario( entity.getId(), pojoEntity );

        ComentarioEntity resp = entityManager.find(ComentarioEntity.class, entity.getId());
		assertEquals( pojoEntity.getId(), resp.getId() );
		assertEquals( pojoEntity.getCalificacion(), resp.getCalificacion() );
        assertEquals( pojoEntity.getContenido(), resp.getContenido() );
        assertEquals( pojoEntity.getEvento(), resp.getEvento() );
        assertEquals( pojoEntity.getFecha(), resp.getFecha() );
        assertEquals( pojoEntity.getFoto(), resp.getFoto() );
    }

    @Test
    public void testUpdateInvalidComentario()
    {
        assertThrows(NullPointerException.class, () -> 
        {
            ComentarioEntity entity = comentarioList.getFirst();
            comentarioService.updateComentario( entity.getId(), null );
        });
    }

    @Test
    public void testUpdateComentarioInvalidID()
    {
        assertThrows(EntityNotFoundException.class, () -> 
        {
            ComentarioEntity entity = comentarioList.getFirst();
            ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);
            pojoEntity.setId( entity.getId() );
            pojoEntity.setEvento( eventoEntity );
            pojoEntity.setCalificacion( generateClasificacion() );
            pojoEntity.setFecha( generateFecha() );
            comentarioService.updateComentario( 0L, pojoEntity );
        });
    }

    /**
     * test de la eliminación de un comentario
     * @throws EntityNotFoundException
     */
    @Test
    public void testDeleteComentario() throws EntityNotFoundException
    {
        ComentarioEntity entity = comentarioList.get(1);
		comentarioService.deleteComentario( entity.getId() );
		ComentarioEntity deleted = entityManager.find(ComentarioEntity.class, entity.getId());
		assertNull(deleted);
    }

    @Test
    public void testDeleteInvalidComentario()
    {
        assertThrows(EntityNotFoundException.class, () -> 
        {
            comentarioService.deleteComentario(0L);
        });
    }


    /**
     * Genera una fecha válida
     * @return Date: la fecha válida
     */
    private Date generateFecha()
    {
        Calendar calendar = Calendar.getInstance(); // crea un nuevo calendario
        calendar.setTime(new Date()); // pone la fecha actual en el calendario
        calendar.add(Calendar.DAY_OF_MONTH, -7); // resta 7 días a la fecha actual
		return calendar.getTime();
    }

    /**
     * Genera una clasificación válida
     * @return Integer: la clasificación válida
     */
    private Integer generateClasificacion()
    {
        return ThreadLocalRandom.current().nextInt(0, 6); // generar un integro entre 0 y 5
    }
    
}
