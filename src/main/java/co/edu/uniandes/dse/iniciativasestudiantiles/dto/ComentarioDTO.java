package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ComentarioDTO 
{
    private Long id;
    
    private String contenido;

    private Date fecha; // formato -> ""

    private Integer calificacion;
    private String foto; // url

    private EventoDTO evento;
}
