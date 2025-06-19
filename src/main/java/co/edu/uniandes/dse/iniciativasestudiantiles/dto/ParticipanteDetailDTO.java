package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import lombok.Data;
import java.util.*;

@Data
public class ParticipanteDetailDTO extends ParticipanteDTO {
    private List<EventoDTO> eventos = new ArrayList<>();
}
