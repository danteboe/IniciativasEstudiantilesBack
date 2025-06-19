package co.edu.uniandes.dse.iniciativasestudiantiles.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.iniciativasestudiantiles.dto.MiembroDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.dto.MiembroDetailDTO;
import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.iniciativasestudiantiles.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.iniciativasestudiantiles.services.MiembroService;

@RestController
@RequestMapping("/miembros")
public class MiembroController {
    
    @Autowired
    private MiembroService miembroService;

    @Autowired
    private ModelMapper modelMapper;

    /**
	 * Busca y devuelve todos los miembros que existen en la aplicacion.
	 *
	 * @return JSONArray {@link MiembroDetailDTO} - Los miembros encontrados en la
	 *         aplicación. Si no hay ninguno retorna una lista vacía.
	 */
   @GetMapping
   @ResponseStatus(code= HttpStatus.OK) 
   public List<MiembroDetailDTO> findAll(){
        List<MiembroEntity> miembros = miembroService.getMiembros();
        return modelMapper.map(miembros, new TypeToken<List<MiembroDetailDTO>>(){}.getType());
       }


    /**
	 * Busca el miembro con el id asociado recibido en la URL y lo devuelve.
	 *
	 * @param miembroId Identificador del miembro que se esta buscando. Este debe ser una
	 *               cadena de dígitos.
	 * @return JSON {@link MiembroDetailDTO} - El miembro buscado
	 */
    @GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public MiembroDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		MiembroEntity miembroEntity = miembroService.getMiembro(id);
		return modelMapper.map(miembroEntity, MiembroDetailDTO.class);
	}

    /**
	 * Crea un nuevo miembro con la informacion que se recibe en el cuerpo de la
	 * petición y se regresa un objeto identico con un id auto-generado por la base
	 * de datos.
	 *
	 * @param miembro {@link MiembroDTO} - EL miembro que se desea guardar.
	 * @return JSON {@link MiembroDTO} - El miembro guardado con el atributo id
	 *         autogenerado.
	 */
    @PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public MiembroDTO create(@RequestBody MiembroDTO miembroDTO) throws IllegalOperationException, EntityNotFoundException {
		MiembroEntity miembroEntity = miembroService.createMiembro(modelMapper.map(miembroDTO, MiembroEntity.class));
		return modelMapper.map(miembroEntity, MiembroDTO.class);
	}

    /**
	 * Actualiza el miembro con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 *
	 * @param miembroId Identificador del miembro que se desea actualizar. Este debe ser
	 *               una cadena de dígitos.
	 * @param miembro   {@link MiembroDTO} El miembro que se desea guardar.
	 * @return JSON {@link MiembroDTO} - El miembro guardada.
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public MiembroDTO update(@PathVariable("id") Long id, @RequestBody MiembroDTO miembroDTO)
			throws EntityNotFoundException, IllegalOperationException {
		MiembroEntity miembroEntity = miembroService.updateMiembro(id, modelMapper.map(miembroDTO, MiembroEntity.class));
		return modelMapper.map(miembroEntity, MiembroDTO.class);
	}

    /**
	 * Borra el miembro con el id asociado recibido en la URL.
	 *
	 * @param miembroId Identificador del miembro que se desea borrar. Este debe ser una
	 *               cadena de dígitos.
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
		miembroService.deleteMiembro(id);
	}
}
