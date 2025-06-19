package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import java.util.Date;

import lombok.Data;

@Data
public class IniciativaDTO 
{
    private String nombre;
    private String logo; // url
    private String descripcion;
    
    private Date fechaCreacion; // formato -> ""

    private Boolean estado;
    private String administrador; // nombre del administrador

    private Long id;
}
