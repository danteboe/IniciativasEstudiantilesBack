package co.edu.uniandes.dse.iniciativasestudiantiles.entities;

import java.util.Date;

import co.edu.uniandes.dse.iniciativasestudiantiles.podam.DateStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Entity
public class ComentarioEntity extends BaseEntity
{

    private String contenido;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha; // formato -> ""

    private Integer calificacion;
    private String foto; // url

    // ==== ASOCIACIONES ====

    // Evento
    @PodamExclude
    @ManyToOne
    private EventoEntity evento;

}