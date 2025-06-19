package co.edu.uniandes.dse.iniciativasestudiantiles.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(IniciativaMiembroService.class)
public class IniciativaMiembroServiceTest {

    @Autowired
    private IniciativaMiembroService iniciativaMiembroService;

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

    void testAddMiembroToIniciativa() throws EntityNotFoundException, IllegalOperationException
    {
        MiembroEntity newMiembro = factory.manufacturePojo(MiembroEntity.class);
        newMiembro.setFechaInscripcion(generateFecha());
        entityManager.persist(newMiembro);

        IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
        iniciativa.setMiembros(miembrosList);
        iniciativa.setEventos(eventosList);
        iniciativa.setConcursos(concursosList);
        entityManager.persist(iniciativa);

        iniciativaMiembroService.addMiembroToIniciativa(iniciativa.getId(),newMiembro.getId());

        MiembroEntity lastMiembro = iniciativaMiembroService.getMiembroFromIniciativa(iniciativa.getId(),newMiembro.getId());
        assertEquals(newMiembro.getId(), lastMiembro.getId());
        assertEquals(newMiembro.getNombre(), lastMiembro.getNombre());
        assertEquals(newMiembro.getFechaInscripcion(), lastMiembro.getFechaInscripcion());
        assertEquals(newMiembro.getCorreoElectronico(), lastMiembro.getCorreoElectronico());
        assertEquals(newMiembro.getTipo(), lastMiembro.getTipo());
        assertEquals(newMiembro.getCodigo(), lastMiembro.getCodigo());
        assertEquals(newMiembro.getEstado(), lastMiembro.getEstado());        

    }

    @Test
    void testGetMiembrosFromIniciativa() throws EntityNotFoundException {
        IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
        iniciativa.setMiembros(miembrosList);
        entityManager.persist(iniciativa);

        List<MiembroEntity> miembros = iniciativaMiembroService.getMiembrosFromIniciativa(iniciativa.getId());

        assertNotNull(miembros);
        assertEquals(miembrosList.size(), miembros.size());
        assertTrue(miembros.containsAll(miembrosList));
    }

    @Test
    void testGetMiembroFromIniciativa() throws EntityNotFoundException, IllegalOperationException {
        IniciativaEntity iniciativa = factory.manufacturePojo(IniciativaEntity.class);
        iniciativa.setMiembros(miembrosList);
        entityManager.persist(iniciativa);

        MiembroEntity miembro = miembrosList.get(0);

        MiembroEntity result = iniciativaMiembroService.getMiembroFromIniciativa(iniciativa.getId(), miembro.getId());

        assertNotNull(result);
        assertEquals(miembro.getId(), result.getId());
        assertEquals(miembro.getNombre(), result.getNombre());
        assertEquals(miembro.getFechaInscripcion(), result.getFechaInscripcion());
        assertEquals(miembro.getCorreoElectronico(), result.getCorreoElectronico());
        assertEquals(miembro.getTipo(), result.getTipo());
        assertEquals(miembro.getCodigo(), result.getCodigo());
        assertEquals(miembro.getEstado(), result.getEstado());
    }

    @Test
    void testGetInvalidMiembroFromIniciativa() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaMiembroService.getMiembroFromIniciativa(iniciativaList.get(0).getId(), 0L);
        });
    }

    @Test
    void testGetInvalidIniciativaMiembro() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaMiembroService.getMiembroFromIniciativa(0L, miembroEntity.getId());
        });
    }

    @Test
    void testAddMiembrosFromIniciativa() throws EntityNotFoundException, IllegalOperationException
    {
        List<MiembroEntity> miembros = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            MiembroEntity miembro = factory.manufacturePojo(MiembroEntity.class);
            miembro.setFechaInscripcion( generateFecha() );
            entityManager.persist(miembro);
            miembros.add(miembro);
        }

        IniciativaEntity iniciativa = iniciativaList.get(0);
        iniciativaMiembroService.addMiembrosToIniciativa(iniciativa.getId(), miembros);

        List<MiembroEntity> miembrosIniciativa = iniciativaMiembroService.getMiembrosFromIniciativa(iniciativa.getId());
        assertEquals(miembros.size(), miembrosIniciativa.size());
    }

    @Test
    void testRemoveMiembroFromIniciativa() throws EntityNotFoundException, IllegalOperationException {
        IniciativaEntity iniciativa = iniciativaList.get(0);
        MiembroEntity miembro = miembrosList.get(0);

        iniciativaMiembroService.removeMiembroFromIniciativa(iniciativa.getId(), miembro.getId());
        assertTrue(iniciativaMiembroService.getMiembrosFromIniciativa(iniciativa.getId()).isEmpty());
    }

    @Test
    void testRemoveInvalidMiembroFromIniciativa() throws EntityNotFoundException
    {
        assertThrows(EntityNotFoundException.class, ()->
        {
            iniciativaMiembroService.removeMiembroFromIniciativa(iniciativaList.get(0).getId(), 0L);
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
