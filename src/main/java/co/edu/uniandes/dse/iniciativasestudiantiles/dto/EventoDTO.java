package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EventoDTO {

    private Long id;

    private String titulo;
    private String imagenPublicitaria;
    private Integer cupos;
    private String descripcion;

    private Date fecha;
    
    private Integer duracion;
    private String lugar;
    private String organizador;
}
