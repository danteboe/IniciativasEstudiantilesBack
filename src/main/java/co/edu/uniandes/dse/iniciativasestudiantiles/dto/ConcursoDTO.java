package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ConcursoDTO {
    private String titulo;
	private String imagen;
	private String descripcion;
	private Integer precio;
	private String premio;
	private String organizado;
	private String ciudad;
    private Date fecha;
    private Long id; 

}
