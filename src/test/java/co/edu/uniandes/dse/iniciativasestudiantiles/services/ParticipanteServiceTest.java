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

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ParticipanteEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ParticipanteService.class)

public class ParticipanteServiceTest {

    @Autowired
    private ParticipanteService participanteService;
    
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ParticipanteEntity> participanteList = new ArrayList<>();

    @BeforeEach
	public void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ParticipanteEntity");;
	}

	private void insertData(){
        // Inserta más participantes de ser necesario
        for (int i = 0; i < 3; i++) {
            ParticipanteEntity participanteEntity = factory.manufacturePojo(ParticipanteEntity.class);
            entityManager.persist(participanteEntity);
            participanteList.add(participanteEntity);
        }
    }

    // TESTS
     
    @Test
    public void testCreateParticipante() throws EntityNotFoundException, IllegalOperationException {
        ParticipanteEntity newEntity = factory.manufacturePojo(ParticipanteEntity.class);

        ParticipanteEntity result = participanteService.createParticipante(newEntity);
        assertNotNull(result);

        ParticipanteEntity entity = entityManager.find(ParticipanteEntity.class,result.getId());

        assertEquals(newEntity.getCarrera(), entity.getCarrera());
        assertEquals(newEntity.getCedula(), entity.getCedula());
        assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        assertEquals(newEntity.getFoto(), entity.getFoto());
        assertEquals(newEntity.getGenero(), entity.getGenero());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test
    public void testGetParticipantes() throws EntityNotFoundException, IllegalOperationException {
        List<ParticipanteEntity> list = participanteService.getParticipantes();
        assertEquals(participanteList.size(), list.size());

        for (ParticipanteEntity entity : list){

            boolean found = false;

            for (ParticipanteEntity storedEntity : participanteList) {
                if (entity.getId().equals(storedEntity.getId())) found = true;
            }

            assertTrue(found);
        }
    }

    @Test
    public void testGetParticipante() throws EntityNotFoundException, IllegalOperationException {
        ParticipanteEntity entity = participanteList.get(0);
		ParticipanteEntity resultEntity = participanteService.getParticipante(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(resultEntity.getCarrera(), entity.getCarrera());
        assertEquals(resultEntity.getCedula(), entity.getCedula());
        assertEquals(resultEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        assertEquals(resultEntity.getFoto(), entity.getFoto());
        assertEquals(resultEntity.getGenero(), entity.getGenero());
        assertEquals(resultEntity.getId(), entity.getId());
        assertEquals(resultEntity.getNombre(), entity.getNombre());
    }

    @Test
    public void testUpdateParticipante() throws EntityNotFoundException, IllegalOperationException{
        ParticipanteEntity entity = participanteList.get(0);
		ParticipanteEntity pojoEntity = factory.manufacturePojo(ParticipanteEntity.class);

		pojoEntity.setId(entity.getId());
		participanteService.updateParticipante(entity.getId(), pojoEntity);

        ParticipanteEntity resp = entityManager.find(ParticipanteEntity.class, entity.getId());

        assertEquals(pojoEntity.getCarrera(), resp.getCarrera());
        assertEquals(pojoEntity.getCedula(), resp.getCedula());
        assertEquals(pojoEntity.getCorreoElectronico(), resp.getCorreoElectronico());
        assertEquals(pojoEntity.getFoto(), resp.getFoto());
        assertEquals(pojoEntity.getGenero(), resp.getGenero());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    @Test
    public void testDeleteParticipante() throws EntityNotFoundException {
        ParticipanteEntity entity = participanteList.get(1);
		participanteService.deleteParticipante(entity.getId());
		ParticipanteEntity deleted = entityManager.find(ParticipanteEntity.class, entity.getId());
		assertNull(deleted);
    }

}
