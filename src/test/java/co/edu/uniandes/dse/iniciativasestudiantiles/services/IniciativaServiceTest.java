package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith( SpringExtension.class )
@DataJpaTest
@Transactional
@Import( IniciativaService.class )
public class IniciativaServiceTest 
{

    @Autowired
    private IniciativaService iniciativaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    
    private List<MiembroEntity> miembrosList = new ArrayList<>();
    private List<EventoEntity> eventosList = new ArrayList<>();
    private List<ConcursoEntity> concursosList = new ArrayList<>();

    private MiembroEntity miembroEntity;
    private EventoEntity eventoEntity;
    private ConcursoEntity concursoEntity;

    private List<IniciativaEntity> iniciativaList = new ArrayList<>();
    

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
        entityManager.getEntityManager().createQuery("delete from MiembroEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ConcursoEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData()
    {
        miembroEntity = factory.manufacturePojo(MiembroEntity.class);
        entityManager.persist(miembroEntity); // persistir un evento para las pruebas
        miembrosList.add(miembroEntity);

        eventoEntity = factory.manufacturePojo(EventoEntity.class);
        entityManager.persist(eventoEntity); // persistir un evento para las pruebas
        eventosList.add(eventoEntity);

        concursoEntity = factory.manufacturePojo(ConcursoEntity.class);
        entityManager.persist(concursoEntity); // persistir un evento para las pruebas
        concursosList.add(concursoEntity);

        // peristir las listas para las pruebas
        for (int i = 0; i < 3; i++) 
        {
            IniciativaEntity iniciativaEntity = factory.manufacturePojo(IniciativaEntity.class);
            iniciativaEntity.setMiembros(miembrosList);
            iniciativaEntity.setEventos(eventosList);
            iniciativaEntity.setConcursos(concursosList);
            iniciativaEntity.setFechaCreacion( generateFecha() );

            entityManager.persist( iniciativaEntity );
            iniciativaList.add( iniciativaEntity );

		}
	}

    // ============= START TESTS

    @Test
    public void testCreateIniciativa() throws IllegalOperationException, EntityNotFoundException
    {
        IniciativaEntity newEntity = factory.manufacturePojo(IniciativaEntity.class);
        newEntity.setMiembros(miembrosList);
        newEntity.setEventos(eventosList);
        newEntity.setConcursos(concursosList);
        newEntity.setFechaCreacion( generateFecha() );

        IniciativaEntity result = iniciativaService.createIniciativa( newEntity );
        assertNotNull(result);

        // Obtener la entidad de la base de datos usando el entityManager
        IniciativaEntity entity = entityManager.find( IniciativaEntity.class, result.getId());

        assertEquals( newEntity.getId(), entity.getId() );
        assertEquals( newEntity.getAdministrador(), entity.getAdministrador() );
        assertEquals( newEntity.getConcursos(), entity.getConcursos() );
        assertEquals( newEntity.getDescripcion(), entity.getDescripcion() );
        assertEquals( newEntity.getEstado(), entity.getEstado() );
        assertEquals( newEntity.getEventos(), entity.getEventos() );
        assertEquals( newEntity.getFechaCreacion(), entity.getFechaCreacion() );
        assertEquals( newEntity.getLogo(), entity.getLogo() );
        assertEquals( newEntity.getMiembros(), entity.getMiembros() );
        assertEquals( newEntity.getNombre(), entity.getNombre() );
    }

    @Test
    public void testCreateInvalidIniciativa()
    {
        IniciativaEntity newEntity = factory.manufacturePojo(IniciativaEntity.class);
        assertThrows(IllegalOperationException.class, () -> 
        {
            iniciativaService.createIniciativa(newEntity);
        });
    }

    @Test
    public void testGetIniciativas()
    {
        List<IniciativaEntity> list = iniciativaService.getIniciativas();
        assertEquals( iniciativaList.size(), list.size() );

        for( IniciativaEntity entity : list )
        {
            boolean found = false;
            for ( IniciativaEntity storedEntity : iniciativaList ) 
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
	public void testGetIniciativa() throws EntityNotFoundException  
    {
		IniciativaEntity entity = iniciativaList.get(0);
		IniciativaEntity resultEntity = iniciativaService.getIniciativa( entity.getId() );
		assertNotNull( resultEntity ); 
		assertEquals( entity.getId(), resultEntity.getId() );
        assertEquals( entity.getAdministrador(), resultEntity.getAdministrador() );
        assertEquals( entity.getConcursos(), resultEntity.getConcursos() );
        assertEquals( entity.getDescripcion(), resultEntity.getDescripcion() );
        assertEquals( entity.getEstado(), resultEntity.getEstado() );
        assertEquals( entity.getEventos(), resultEntity.getEventos() );
        assertEquals( entity.getFechaCreacion(), resultEntity.getFechaCreacion() );
        assertEquals( entity.getLogo(), resultEntity.getLogo() );
        assertEquals( entity.getMiembros(), resultEntity.getMiembros() );
        assertEquals( entity.getNombre(), resultEntity.getNombre() );
	}

    @Test
    public void testGetInvalidIniciativa() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, () -> 
        {
            iniciativaService.getIniciativa( 0L );
        });
    }

    @Test
    public void testUpdateIniciativa() throws IllegalOperationException, EntityNotFoundException
    {
        IniciativaEntity entity = iniciativaList.get(0);
		IniciativaEntity pojoEntity = factory.manufacturePojo(IniciativaEntity.class);
		pojoEntity.setId( entity.getId() );
        pojoEntity.setMiembros(miembrosList);
        pojoEntity.setEventos(eventosList);
        pojoEntity.setConcursos(concursosList);
        pojoEntity.setFechaCreacion( generateFecha() );
		iniciativaService.updateIniciativa( entity.getId(), pojoEntity );

        IniciativaEntity resp = entityManager.find(IniciativaEntity.class, entity.getId());
		assertEquals( pojoEntity.getId(), resp.getId() );
		assertEquals( pojoEntity.getAdministrador(), resp.getAdministrador() );
        assertEquals( pojoEntity.getConcursos(), resp.getConcursos() );
        assertEquals( pojoEntity.getDescripcion(), resp.getDescripcion() );
        assertEquals( pojoEntity.getEstado(), resp.getEstado() );
        assertEquals( pojoEntity.getEventos(), resp.getEventos() );
        assertEquals( pojoEntity.getFechaCreacion(), resp.getFechaCreacion() );
        assertEquals( pojoEntity.getLogo(), resp.getLogo() );
        assertEquals( pojoEntity.getMiembros(), resp.getMiembros() );
        assertEquals( pojoEntity.getNombre(), resp.getNombre() );
    }

    @Test
    public void testUpdateInvalidIniciativa() throws EntityNotFoundException
    {
        assertThrows(NullPointerException.class, () -> 
        {
            IniciativaEntity entity = iniciativaList.get(0);
            iniciativaService.updateIniciativa(entity.getId(), null);
        });
    }

    @Test
    public void testUpdateIniciativaInvalidID() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, () -> 
        {
            IniciativaEntity entity = iniciativaList.get(0);
            IniciativaEntity pojoEntity = factory.manufacturePojo(IniciativaEntity.class);
            pojoEntity.setId( entity.getId() );
            pojoEntity.setMiembros(miembrosList);
            pojoEntity.setEventos(eventosList);
            pojoEntity.setConcursos(concursosList);
            pojoEntity.setFechaCreacion( generateFecha() );
            iniciativaService.updateIniciativa(0l, pojoEntity);
        });
    }

    /**
     * test de la eliminación de un comentario
     * @throws co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException 
     */
    @Test
    public void testDeleteIniciativa() throws EntityNotFoundException
    {
        IniciativaEntity entity = iniciativaList.get(1);
		iniciativaService.deleteIniciativa( entity.getId() );
		IniciativaEntity deleted = entityManager.find(IniciativaEntity.class, entity.getId());
		assertNull(deleted);
    }

    @Test
    public void testDeleteInvalidComentario() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, () -> 
        {
            iniciativaService.deleteIniciativa(0L);
        });
    }

    /**
     * Genera una fecha válida
     * @return Date: la fecha valida
     */
    private Date generateFecha()
    {
        Calendar calendar = Calendar.getInstance(); // crea un nuevo calendario
		calendar.setTime( new Date() ); // pone la fecha actual en el calendario
		return calendar.getTime();
    }

    
}
