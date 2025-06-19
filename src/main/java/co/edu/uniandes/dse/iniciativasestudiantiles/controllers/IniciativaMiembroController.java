package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.MiembroDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.MiembroDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.IniciativaMiembroService;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/iniciativas")
public class IniciativaMiembroController {

    @Autowired
    private IniciativaMiembroService iniciativaMiembroService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Agrega un miembro a una iniciativa con la información de la URL.
     *
     * @param iniciativaId Identificador de la iniciativa.
     * @param miembroId    Identificador del miembro.
     * @return {@link MiembroDTO} El miembro agregado a la iniciativa.
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @PostMapping(value = "/{iniciativaId}/miembros/{miembroId}")
    @ResponseStatus(HttpStatus.OK)
    public MiembroDTO addMiembro(@PathVariable("iniciativaId") Long iniciativaId, @PathVariable("miembroId") Long miembroId)
            throws EntityNotFoundException, IllegalOperationException {
        MiembroEntity miembroEntity = iniciativaMiembroService.addMiembroToIniciativa(iniciativaId, miembroId);
        return modelMapper.map(miembroEntity, MiembroDTO.class);
    }

    /**
     * Obtiene todos los miembros asociados a una iniciativa.
     *
     * @param iniciativaId Identificador de la iniciativa.
     * @return Lista de {@link MiembroDetailDTO} - Los miembros asociados a la iniciativa.
     * @throws EntityNotFoundException
     */
    @GetMapping(value = "/{iniciativaId}/miembros")
    @ResponseStatus(HttpStatus.OK)
    public List<MiembroDetailDTO> getMiembros(@PathVariable("iniciativaId") Long iniciativaId) throws EntityNotFoundException {
        List<MiembroEntity> miembrosList = iniciativaMiembroService.getMiembrosFromIniciativa(iniciativaId);
        return modelMapper.map(miembrosList, new TypeToken<List<MiembroDetailDTO>>() {
        }.getType());
    }

    /**
     * Obtiene un miembro específico de una iniciativa.
     *
     * @param iniciativaId Identificador de la iniciativa.
     * @param miembroId    Identificador del miembro.
     * @return {@link MiembroDetailDTO} El miembro de la iniciativa.
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @GetMapping(value = "/{iniciativaId}/miembros/{miembroId}")
    @ResponseStatus(HttpStatus.OK)
    public MiembroDetailDTO getMiembro(@PathVariable("iniciativaId") Long iniciativaId, @PathVariable("miembroId") Long miembroId)
            throws EntityNotFoundException, IllegalOperationException {
        MiembroEntity miembroEntity = iniciativaMiembroService.getMiembroFromIniciativa(iniciativaId, miembroId);
        return modelMapper.map(miembroEntity, MiembroDetailDTO.class);
    }

    /**
     * Reemplaza los miembros asociados a una iniciativa.
     *
     * @param iniciativaId Identificador de la iniciativa.
     * @param miembros     Lista de {@link MiembroDetailDTO} a asociar con la iniciativa.
     * @return Lista de {@link MiembroDetailDTO} - Los miembros actualizados.
     * @throws EntityNotFoundException
     */
    @PutMapping(value = "/{iniciativaId}/miembros")
    @ResponseStatus(HttpStatus.OK)
    public List<MiembroDetailDTO> replaceMiembros(@PathVariable("iniciativaId") Long iniciativaId,
                                                  @RequestBody List<MiembroDetailDTO> miembros) throws EntityNotFoundException {
        List<MiembroEntity> miembrosList = modelMapper.map(miembros, new TypeToken<List<MiembroEntity>>() {
        }.getType());
        List<MiembroEntity> updatedMiembros = iniciativaMiembroService.addMiembrosToIniciativa(iniciativaId, miembrosList);
        return modelMapper.map(updatedMiembros, new TypeToken<List<MiembroDetailDTO>>() {
        }.getType());
    }

    /**
     * Elimina la asociación de un miembro con una iniciativa.
     *
     * @param iniciativaId Identificador de la iniciativa.
     * @param miembroId    Identificador del miembro.
     * @throws EntityNotFoundException, IllegalOperationException
     */
    @DeleteMapping(value = "/{iniciativaId}/miembros/{miembroId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMiembro(@PathVariable("iniciativaId") Long iniciativaId, @PathVariable("miembroId") Long miembroId)
            throws EntityNotFoundException, IllegalOperationException {
        iniciativaMiembroService.removeMiembroFromIniciativa(iniciativaId, miembroId);
    }
}
