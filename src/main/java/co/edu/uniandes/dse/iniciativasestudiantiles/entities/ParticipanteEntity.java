package co.edu.uniandes.dse.iniciativasestudiantiles.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class ParticipanteEntity extends BaseEntity
{

    private String nombre;
    private String foto; // url
    private String correoElectronico;
    private Integer cedula;
    private String genero;
    private String carrera;

    // ==== ASOCIACIONES ====

    //EVENTO
    @PodamExclude
    @ManyToMany
    private List<EventoEntity> eventos = new ArrayList<>();

}
