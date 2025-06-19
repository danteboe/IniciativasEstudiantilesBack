package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ParticipanteEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EventoParticipanteService.class)
public class EventoParticipanteServiceTest {

    @Autowired
    private EventoParticipanteService eventoParticipanteService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EventoEntity> eventosList = new ArrayList<>();
    private List<ParticipanteEntity> participantesList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ParticipanteEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ParticipanteEntity participante = factory.manufacturePojo(ParticipanteEntity.class);
            entityManager.persist(participante);
            participantesList.add(participante);
        }

        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            entityManager.persist(evento);
            eventosList.add(evento);
            if (i == 0) {
                participantesList.get(i).setEventos(eventosList);;
                evento.getParticipantes().add(participantesList.get(i));
            }
        }
    }

    @Test
    void testAddParticipante() throws EntityNotFoundException, IllegalOperationException {
        ParticipanteEntity newParticipante = factory.manufacturePojo(ParticipanteEntity.class);
        entityManager.persist(newParticipante);

        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        evento.setParticipantes(participantesList);
        entityManager.persist(evento);

        eventoParticipanteService.addParticipante(evento.getId(),newParticipante.getId());

        ParticipanteEntity participante = eventoParticipanteService.getParticipante(evento.getId(),newParticipante.getId());
        assertEquals(newParticipante.getCarrera(), participante.getCarrera());
        assertEquals(newParticipante.getCedula(), participante.getCedula());
        assertEquals(newParticipante.getCorreoElectronico(), participante.getCorreoElectronico());
        assertEquals(newParticipante.getFoto(), participante.getFoto());
        assertEquals(newParticipante.getGenero(), participante.getGenero());
        assertEquals(newParticipante.getId(), participante.getId());
        assertEquals(newParticipante.getNombre(), participante.getNombre());
    }

    @Test
    public void testGetParticipante() throws EntityNotFoundException, IllegalOperationException {
        ParticipanteEntity newParticipante = participantesList.get(0);
        entityManager.persist(newParticipante);

        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        evento.setParticipantes(participantesList);
        entityManager.persist(evento);

		ParticipanteEntity participante = eventoParticipanteService.getParticipante(evento.getId(), newParticipante.getId());
        assertNotNull(newParticipante);
        assertEquals(newParticipante.getCarrera(), participante.getCarrera());
        assertEquals(newParticipante.getCedula(), participante.getCedula());
        assertEquals(newParticipante.getCorreoElectronico(), participante.getCorreoElectronico());
        assertEquals(newParticipante.getFoto(), participante.getFoto());
        assertEquals(newParticipante.getGenero(), participante.getGenero());
        assertEquals(newParticipante.getId(), participante.getId());
        assertEquals(newParticipante.getNombre(), participante.getNombre());
    }

    @Test
    public void testGetParticipantes() throws EntityNotFoundException, IllegalOperationException {
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        evento.setParticipantes(participantesList);
        entityManager.persist(evento);

        List<ParticipanteEntity> listParticipantes = eventoParticipanteService.getParticipantes(evento.getId());
        assertEquals(participantesList.size(), listParticipantes.size());

        for (ParticipanteEntity entity : listParticipantes){

            boolean found = false;

            for (ParticipanteEntity storedEntity : participantesList) {
                if (entity.getId().equals(storedEntity.getId())) found = true;
            }

            assertTrue(found);
        }
    }

    @Test
    void testReplaceParticipantes() throws EntityNotFoundException {
        EventoEntity evento = eventosList.get(0);
        List<ParticipanteEntity> list = participantesList.subList(1, 3);
        eventoParticipanteService.replaceParticipantes(evento.getId(), list);

        for (ParticipanteEntity participante : list) {
            ParticipanteEntity part = entityManager.find(ParticipanteEntity.class, participante.getId());
            assertTrue(part.getEventos().equals(participante.getEventos()));
        }
    }
    
    @Test
    public void testRemoveParticipante() throws EntityNotFoundException {
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        evento.setParticipantes(participantesList);
        entityManager.persist(evento);

        ParticipanteEntity participante = participantesList.get(1);
		eventoParticipanteService.removeParticipante(evento.getId(), participante.getId());
		assertFalse(participantesList.contains(participante));

    }

}
