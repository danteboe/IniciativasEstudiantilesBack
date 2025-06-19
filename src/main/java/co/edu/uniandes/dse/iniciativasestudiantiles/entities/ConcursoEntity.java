package co.edu.uniandes.dse.iniciativasestudiantiles.entities;

import java.util.*;

import co.edu.uniandes.dse.iniciativasestudiantiles.podam.DateStrategy;
import jakarta.persistence.Entity;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;


@Data
@Entity
public class ConcursoEntity extends BaseEntity{
	
	private String titulo;
	private String imagen;
	private String descripcion;
	private Integer precio;
	private String premio;

	@Temporal(TemporalType.DATE)
	@PodamStrategyValue(DateStrategy.class)
	private Date fecha;
	
	private String organizado;
	private String ciudad;

	// ==== ASOCIACIONES ====

    // INICIATIVA
	@PodamExclude
    @ManyToMany(mappedBy = "concursos")
    private List<IniciativaEntity> iniciativas = new ArrayList<>();

}