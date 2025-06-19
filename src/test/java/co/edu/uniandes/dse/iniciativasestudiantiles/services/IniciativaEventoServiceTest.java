package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(IniciativaEventoService.class)
public class IniciativaEventoServiceTest 
{
    @Autowired
    private IniciativaEventoService iniciativaEventoService;

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
        eventoEntity.setFecha( generateFecha());
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
	void testAddEventoToIniciativa() throws EntityNotFoundException, IllegalOperationException 
    {
		EventoEntity newEvento = factory.manufacturePojo(EventoEntity.class);
		newEvento.setFecha( generateFecha() );
		entityManager.persist( newEvento );
		
		IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
        iniciativa.setMiembros(miembrosList);
        iniciativa.setEventos(eventosList);
        iniciativa.setConcursos(concursosList);
        iniciativa.setFechaCreacion( generateFecha() );
		entityManager.persist( iniciativa );
		
		EventoEntity lastEvento = iniciativaEventoService.addEventoToIniciativa(  iniciativa.getId(), newEvento.getId() );
		
		assertEquals(newEvento.getId(), lastEvento.getId());
        assertEquals(newEvento.getDescripcion(), lastEvento.getDescripcion());
        assertEquals(newEvento.getFecha(), lastEvento.getFecha());
        assertEquals(newEvento.getLugar(), lastEvento.getLugar());
        assertEquals(newEvento.getComentarios(), lastEvento.getComentarios());
        assertEquals(newEvento.getCupos(), lastEvento.getCupos());
        assertEquals(newEvento.getDuracion(), lastEvento.getDuracion());
        assertEquals(newEvento.getTitulo(), lastEvento.getTitulo());
        assertEquals(newEvento.getImagenPublicitaria(), lastEvento.getImagenPublicitaria());
        assertEquals(newEvento.getIniciativa(), lastEvento.getIniciativa());
	}

    @Test
	void testAddInvalidEventoToIniciativa() 
    {
		assertThrows(EntityNotFoundException.class, ()->{
			iniciativaEventoService.addEventoToIniciativa(iniciativaList.get(0).getId(), 0L);
		});
	}

    @Test
	void testAddEventoToInvalidIniciativa() 
    {
		assertThrows(EntityNotFoundException.class, ()->{
			iniciativaEventoService.addEventoToIniciativa(0L, eventoEntity.getId());
		});
	}

	@Test
	void testGetEventosFromIniciativa() throws EntityNotFoundException 
    {
		List<EventoEntity> eventoEntities = iniciativaEventoService.getEventosFromIniciativa(iniciativaList.get(0).getId());

		assertEquals(eventosList.size(), eventoEntities.size());

		for (int i = 0; i < eventosList.size(); i++) {
			assertTrue(eventoEntities.contains(eventosList.get(0)));
		}
	}

    @Test
    void testGetEventoFromIniciativa() throws EntityNotFoundException, IllegalOperationException
    {
        EventoEntity evento = eventosList.get(0);
        IniciativaEntity iniciativa = iniciativaList.get(0);

        if ( evento != null && iniciativa != null )
        {
            EventoEntity eventoEntity = iniciativaEventoService.getEventoFromIniciativa(iniciativa.getId(), evento.getId());
            assertEquals(evento.getId(), eventoEntity.getId());
            assertEquals(evento.getDescripcion(), eventoEntity.getDescripcion());
            assertEquals(evento.getFecha(), eventoEntity.getFecha());
            assertEquals(evento.getLugar(), eventoEntity.getLugar());
            assertEquals(evento.getComentarios(), eventoEntity.getComentarios());
            assertEquals(evento.getCupos(), eventoEntity.getCupos());
            assertEquals(evento.getDuracion(), eventoEntity.getDuracion());
            assertEquals(evento.getTitulo(), eventoEntity.getTitulo());
            assertEquals(evento.getImagenPublicitaria(), eventoEntity.getImagenPublicitaria());
            assertEquals(evento.getIniciativa(), eventoEntity.getIniciativa());
        }
    }

    @Test
    void testGetInvalidEventoFromIniciativa() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaEventoService.getEventoFromIniciativa(iniciativaList.get(0).getId(), 0L);
        });
    }

    @Test
    void testGetInvalidIniciativaEvento() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaEventoService.getEventoFromIniciativa(0L, eventosList.get(0).getId());
        });
    }

    @Test
    void testAddeEventosFromIniciativa() throws EntityNotFoundException, IllegalOperationException
    {
        List<EventoEntity> eventos = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            evento.setFecha( generateFecha() );
            entityManager.persist(evento);
            eventos.add(evento);
        }

        IniciativaEntity iniciativa = iniciativaList.get(0);
        iniciativaEventoService.addEventosToIniciativa(iniciativa.getId(), eventos);

        List<EventoEntity> eventosIniciativa = iniciativaEventoService.getEventosFromIniciativa(iniciativa.getId());
        assertEquals(eventos.size(), eventosIniciativa.size());
    }

    @Test
    void testAddInvalidEventosToIniciativa() throws EntityNotFoundException
    {
        List<EventoEntity> eventos = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            evento.setFecha( generateFecha() );
            entityManager.persist(evento);
            eventos.add(evento);
        }

        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaEventoService.addEventosToIniciativa(0L, eventos);
        });
    }

    @Test
    void testRemoveEventoFromIniciativa() throws EntityNotFoundException, IllegalOperationException
    {
        IniciativaEntity iniciativa = iniciativaList.get(0);
        EventoEntity evento = eventosList.get(0);

        iniciativaEventoService.removeEventoFromIniciativa(iniciativa.getId(), evento.getId());
        assertTrue(iniciativaEventoService.getEventosFromIniciativa(iniciativa.getId()).isEmpty());
    }

    @Test
    void testRemoveInvalidEventoFromIniciativa() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaEventoService.removeEventoFromIniciativa(iniciativaList.get(0).getId(), 0L);
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
