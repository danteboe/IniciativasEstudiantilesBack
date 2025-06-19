package co.edu.uniandes.dse.iniciativasestudiantiles.entities;

import java.util.*;

import co.edu.uniandes.dse.iniciativasestudiantiles.podam.DateStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Entity
public class IniciativaEntity extends BaseEntity {

    private String nombre;
    private String logo; // url
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaCreacion; // formato -> ""

    private Boolean estado;
    private String administrador; // nombre del administrador

    // ==== ASOCIACIONES ====

    // EVENTO
    @PodamExclude
    @OneToMany( mappedBy = "iniciativa" )
    private List<EventoEntity> eventos = new ArrayList<>();

    // CONCURSO
    @PodamExclude
    @ManyToMany
    private List<ConcursoEntity> concursos = new ArrayList<>() ;

    // MIEMBRO
    @PodamExclude
    @ManyToMany(mappedBy = "iniciativas" )
    private List<MiembroEntity> miembros = new ArrayList<>();
    
}
