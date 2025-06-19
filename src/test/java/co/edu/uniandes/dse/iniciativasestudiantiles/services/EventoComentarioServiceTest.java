package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EventoComentarioService.class)
class EventoComentarioServiceTest {

    @Autowired
    private EventoComentarioService eventoComentarioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EventoEntity> eventosList = new ArrayList<>();
    private List<ComentarioEntity> comentariosList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EventoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            entityManager.persist(comentario);
            comentariosList.add(comentario);
        }

        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            entityManager.persist(evento);
            eventosList.add(evento);
            if (i == 0) {
                comentariosList.get(i).setEvento(evento);
                evento.getComentarios().add(comentariosList.get(i));
            }
        }
    }

    @Test
    void testAddComentario() throws EntityNotFoundException {
        EventoEntity evento = eventosList.get(0);
        ComentarioEntity comentario = comentariosList.get(1);
        ComentarioEntity response = eventoComentarioService.addComentario(evento.getId(), comentario.getId());

        assertNotNull(response);
        assertEquals(comentario.getId(), response.getId());
    }

    @Test
    void testAddInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            EventoEntity evento = eventosList.get(0);
            eventoComentarioService.addComentario(evento.getId(), 0L);
        });
    }

    @Test
    void testAddComentarioInvalidEvento() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity comentario = comentariosList.get(1);
            eventoComentarioService.addComentario(0L, comentario.getId());
        });
    }

    @Test
    void testGetComentarios() throws EntityNotFoundException {
        List<ComentarioEntity> list = eventoComentarioService.getComentarios(eventosList.get(0).getId());
        assertEquals(1, list.size());
    }

    @Test
    void testGetComentariosInvalidEvento() {
        assertThrows(EntityNotFoundException.class, () -> {
            eventoComentarioService.getComentarios(0L);
        });
    }

    @Test
    void testGetComentario() throws EntityNotFoundException, IllegalOperationException {
        EventoEntity evento = eventosList.get(0);
        ComentarioEntity comentario = comentariosList.get(0);
        ComentarioEntity response = eventoComentarioService.getComentario(evento.getId(), comentario.getId());

        assertEquals(comentario.getId(), response.getId());
        assertEquals(comentario.getContenido(), response.getContenido());
    }

    @Test
    void testGetComentarioInvalidEvento() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity comentario = comentariosList.get(0);
            eventoComentarioService.getComentario(0L, comentario.getId());
        });
    }

    @Test
    void testGetInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            EventoEntity evento = eventosList.get(0);
            eventoComentarioService.getComentario(evento.getId(), 0L);
        });
    }

    @Test
    void testGetComentarioNoAsociado() {
        assertThrows(IllegalOperationException.class, () -> {
            EventoEntity evento = eventosList.get(0);
            ComentarioEntity comentario = comentariosList.get(1);
            eventoComentarioService.getComentario(evento.getId(), comentario.getId());
        });
    }

    @Test
    void testReplaceComentarios() throws EntityNotFoundException {
        EventoEntity evento = eventosList.get(0);
        List<ComentarioEntity> list = comentariosList.subList(1, 3);
        eventoComentarioService.replaceComentarios(evento.getId(), list);

        for (ComentarioEntity comentario : list) {
            ComentarioEntity c = entityManager.find(ComentarioEntity.class, comentario.getId());
            assertTrue(c.getEvento().equals(evento));
        }
    }

    @Test
    void testReplaceInvalidComentarios() {
        assertThrows(EntityNotFoundException.class, () -> {
            EventoEntity evento = eventosList.get(0);

            List<ComentarioEntity> comentarios = new ArrayList<>();
            ComentarioEntity newComentario = factory.manufacturePojo(ComentarioEntity.class);
            newComentario.setId(0L);
            comentarios.add(newComentario);

            eventoComentarioService.replaceComentarios(evento.getId(), comentarios);
        });
    }

    @Test
    void testReplaceComentariosInvalidEvento() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ComentarioEntity> list = comentariosList.subList(1, 3);
            eventoComentarioService.replaceComentarios(0L, list);
        });
    }

    @Test
    void testRemoveComentario() throws EntityNotFoundException {
        EventoEntity evento = eventosList.get(0);
        ComentarioEntity comentario = comentariosList.get(0);
        eventoComentarioService.removeComentario(evento.getId(), comentario.getId());

        List<ComentarioEntity> comentarios = eventoComentarioService.getComentarios(evento.getId());
        assertFalse(comentarios.contains(comentario));
    }

    @Test
    void testRemoveComentarioInvalidEvento() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity comentario = comentariosList.get(0);
            eventoComentarioService.removeComentario(0L, comentario.getId());
        });
    }

    @Test
    void testRemoveInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            EventoEntity evento = eventosList.get(0);
            eventoComentarioService.removeComentario(evento.getId(), 0L);
        });
    }
}