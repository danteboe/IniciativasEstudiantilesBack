package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EventoService.class)
public class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EventoEntity> eventosList = new ArrayList<>();
    private EventoEntity eventoEntity;

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        eventoEntity = factory.manufacturePojo(EventoEntity.class);
        entityManager.persist(eventoEntity); // persistir un evento para las pruebas
        eventosList.add(eventoEntity);

        // Inserta más eventos si es necesario
        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            entityManager.persist(evento);
            eventosList.add(evento);
        }
    }

    // TESTS

    @Test
    public void testCreateEvento() throws IllegalOperationException, EntityNotFoundException {
    EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
    
    newEntity.setFecha(generateFecha()); 

    EventoEntity result = eventoService.createEvento(newEntity);
    assertNotNull(result);

    EventoEntity entity = entityManager.find(EventoEntity.class, result.getId());

    assertEquals(newEntity.getId(), entity.getId());
    assertEquals(newEntity.getTitulo(), entity.getTitulo());
    assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    assertEquals(newEntity.getLugar(), entity.getLugar());
    assertEquals(newEntity.getCupos(), entity.getCupos());
    assertEquals(newEntity.getImagenPublicitaria(), entity.getImagenPublicitaria());
    assertEquals(newEntity.getDuracion(), entity.getDuracion());
    assertEquals(newEntity.getFecha(), entity.getFecha());
}

    @Test
    public void testGetEventos() {
        List<EventoEntity> list = eventoService.getEventos();
        assertEquals(eventosList.size(), list.size());

        for (EventoEntity entity : list) {
            boolean found = false;
            for (EventoEntity storedEntity : eventosList) {
                if (entity.getId().equals(storedEntity.getId())) found = true;
            }
            assertTrue(found);
        }
    }

    @Test
    public void testGetEvento() throws EntityNotFoundException {
        EventoEntity entity = eventosList.get(0);
        EventoEntity resultEntity = eventoService.getEvento(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getTitulo(), resultEntity.getTitulo());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getLugar(), resultEntity.getLugar());
        assertEquals(entity.getCupos(), resultEntity.getCupos());
        assertEquals(entity.getImagenPublicitaria(), resultEntity.getImagenPublicitaria());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getFecha(), resultEntity.getFecha());
    }

    @Test
    public void testUpdateEvento() throws IllegalOperationException, EntityNotFoundException {
    EventoEntity entity = eventosList.get(0);
    EventoEntity pojoEntity = factory.manufacturePojo(EventoEntity.class);
    
    pojoEntity.setFecha(generateFecha()); 
    pojoEntity.setId(entity.getId());

    eventoService.updateEvento(entity.getId(), pojoEntity);

    EventoEntity resp = entityManager.find(EventoEntity.class, entity.getId());

    assertEquals(pojoEntity.getId(), resp.getId());
    assertEquals(pojoEntity.getTitulo(), resp.getTitulo());
    assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    assertEquals(pojoEntity.getLugar(), resp.getLugar());
    assertEquals(pojoEntity.getCupos(), resp.getCupos());
    assertEquals(pojoEntity.getImagenPublicitaria(), resp.getImagenPublicitaria());
    assertEquals(pojoEntity.getDuracion(), resp.getDuracion());
    assertEquals(pojoEntity.getFecha(), resp.getFecha());
}

    @Test
    public void testDeleteEvento() throws EntityNotFoundException {
        EventoEntity entity = eventosList.get(1);
        eventoService.deleteEvento(entity.getId());
        EventoEntity deleted = entityManager.find(EventoEntity.class, entity.getId());
        assertNull(deleted);
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