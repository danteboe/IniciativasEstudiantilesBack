package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import java.util.Date;
import lombok.Data;

@Data
public class ParticipanteDTO {

    private Long id;
    private String nombre;
    private String foto;
    private String correoElectronico;
    private Integer cedula;
    private String genero;
    private String carrera;
    private Date fecha;
    
}
