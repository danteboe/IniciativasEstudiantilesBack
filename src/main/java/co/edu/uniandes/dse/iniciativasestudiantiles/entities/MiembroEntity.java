package co.edu.uniandes.dse.iniciativasestudiantiles.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class MiembroEntity extends BaseEntity
{
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fechaInscripcion;

    private String nombre;
    private String foto;
    private String correoElectronico;
    private String tipo;
    private Integer codigo;
    private Estado estado;

    // ==== ASOCIACIONES ====

    // INICIATIVA
    @PodamExclude
    @ManyToMany
    private List<IniciativaEntity> iniciativas = new ArrayList<>();


}
