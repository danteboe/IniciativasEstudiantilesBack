package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.Estado;
import lombok.Data;

import java.util.Date;

@Data
public class MiembroDTO {
    private Long id;
    private Date fechaInscripcion;
    private String nombre;
    private String foto;
    private String correoElectronico;
    private String tipo;
    private Integer codigo;
    private Estado estado;

}
