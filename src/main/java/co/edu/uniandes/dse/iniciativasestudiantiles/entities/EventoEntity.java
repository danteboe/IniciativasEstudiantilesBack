package co.edu.uniandes.dse.iniciativasestudiantiles.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.uniandes.dse.iniciativasestudiantiles.podam.DateStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Entity
public class EventoEntity extends BaseEntity
{

    private String titulo;
    private String imagenPublicitaria; // url
    private Integer cupos;
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha; // formato -> ""
    
    private Integer duracion;
    private String lugar;

    // ==== ASOCIACIONES ====

    // INICIATIVA
    @PodamExclude
    @ManyToOne
    private IniciativaEntity iniciativa;

    // PARTICIPANTE
    @PodamExclude
    @ManyToMany(mappedBy = "eventos")
    private List<ParticipanteEntity> participantes = new ArrayList<>();

    // COMENTARIO
    @PodamExclude
    @OneToMany(mappedBy="evento")
    private List<ComentarioEntity> comentarios = new ArrayList<>();

}
