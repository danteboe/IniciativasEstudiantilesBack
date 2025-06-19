package co.edu.uniandes.dse.iniciativasestudiantiles.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MiembroService.class)
public class MiembroServiceTest {

    @Autowired
    private MiembroService miembroService;
    
    @Autowired
    private TestEntityManager entityManager;
    private PodamFactory factory = new PodamFactoryImpl();

    private List<MiembroEntity> miembroList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MiembroEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MiembroEntity miembroEntity = factory.manufacturePojo(MiembroEntity.class);
            miembroEntity.setFechaInscripcion(generateFecha());
            entityManager.persist(miembroEntity);
            miembroList.add(miembroEntity);
        }
    }

    @Test
    public void testCreateMiembro() throws EntityNotFoundException, IllegalOperationException {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setFechaInscripcion(generateFecha());
        MiembroEntity result = miembroService.createMiembro(newEntity);
        assertNotNull(result);
        MiembroEntity entity = entityManager.find(MiembroEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        assertEquals(newEntity.getFoto(), entity.getFoto());
        assertEquals(newEntity.getFechaInscripcion(), entity.getFechaInscripcion());
        assertEquals(newEntity.getEstado(), entity.getEstado());
        assertEquals(newEntity.getCodigo(), entity.getCodigo());
    }

    @Test
    public void testCreateMiembroInvalidNombre() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setNombre("");
        newEntity.setFechaInscripcion(generateFecha());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testCreateMiembroInvalidFoto() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setFoto("");
        newEntity.setFechaInscripcion(generateFecha());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testCreateMiembroInvalidCorreoElectronico() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setCorreoElectronico("");
        newEntity.setFechaInscripcion(generateFecha());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testCreateMiembroInvalidFechaInscripcion() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // Set date to tomorrow
        newEntity.setFechaInscripcion(calendar.getTime());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testCreateMiembroInvalidEstado() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setEstado(null);
        newEntity.setFechaInscripcion(generateFecha());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testCreateMiembroInvalidTipo() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setTipo("");
        newEntity.setFechaInscripcion(generateFecha());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testCreateMiembroInvalidCodigo() {
        MiembroEntity newEntity = factory.manufacturePojo(MiembroEntity.class);
        newEntity.setCodigo(null);
        newEntity.setFechaInscripcion(generateFecha());
        assertThrows(IllegalOperationException.class, () -> {
            miembroService.createMiembro(newEntity);
        });
    }

    @Test
    public void testGetMiembros() {
        List<MiembroEntity> list = miembroService.getMiembros();
        assertEquals(miembroList.size(), list.size());

        for (MiembroEntity entity : list) {
            boolean found = false;
            for (MiembroEntity storedEntity : miembroList) {
                if (entity.getId().equals(storedEntity.getId())) found = true;
            }
            assertTrue(found);
        }
    }

    @Test
    public void testGetMiembro() throws EntityNotFoundException {
        MiembroEntity entity = miembroList.get(0);
        MiembroEntity resultEntity = miembroService.getMiembro(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(resultEntity.getNombre(), entity.getNombre());
        assertEquals(resultEntity.getCorreoElectronico(), entity.getCorreoElectronico());
        assertEquals(resultEntity.getFoto(), entity.getFoto());
        assertEquals(resultEntity.getFechaInscripcion(), entity.getFechaInscripcion());
        assertEquals(resultEntity.getEstado(), entity.getEstado());
        assertEquals(resultEntity.getCodigo(), entity.getCodigo());
    }

    @Test
    public void testGetMiembroInvalidId() {
        assertThrows(EntityNotFoundException.class, () -> {
            miembroService.getMiembro(0L);
        });
    }

    @Test
    public void testUpdateMiembro() throws IllegalOperationException, EntityNotFoundException {
        MiembroEntity entity = miembroList.get(0);
        MiembroEntity pojoEntity = factory.manufacturePojo(MiembroEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setFechaInscripcion(generateFecha());
        miembroService.updateMiembro(entity.getId(), pojoEntity);

        MiembroEntity resp = entityManager.find(MiembroEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getCorreoElectronico(), resp.getCorreoElectronico());
        assertEquals(pojoEntity.getFoto(), resp.getFoto());
        assertEquals(pojoEntity.getFechaInscripcion(), resp.getFechaInscripcion());
        assertEquals(pojoEntity.getEstado(), resp.getEstado());
        assertEquals(pojoEntity.getCodigo(), resp.getCodigo());
    }

    @Test
    public void testUpdateMiembroInvalidId() {
        MiembroEntity pojoEntity = factory.manufacturePojo(MiembroEntity.class);
        pojoEntity.setFechaInscripcion(generateFecha());
        assertThrows(EntityNotFoundException.class, () -> {
            miembroService.updateMiembro(0L, pojoEntity);
        });
    }

    @Test
    public void testDeleteMiembro() throws EntityNotFoundException {
        MiembroEntity entity = miembroList.get(1);
        miembroService.deleteMiembro(entity.getId());
        MiembroEntity deleted = entityManager.find(MiembroEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    public void testDeleteMiembroInvalidId() {
        assertThrows(EntityNotFoundException.class, () -> {
            miembroService.deleteMiembro(0L);
        });
    }

    private Date generateFecha() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTime();
    }
}