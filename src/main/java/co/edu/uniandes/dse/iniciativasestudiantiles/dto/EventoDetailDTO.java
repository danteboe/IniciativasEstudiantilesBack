package co.edu.uniandes.dse.iniciativasestudiantiles.dto;


import lombok.Data;
import java.util.*;

@Data
public class EventoDetailDTO extends EventoDTO {
    // COMENTARIOS
    private List<ComentarioDTO> comentarios = new ArrayList<>();

    // PARTICIPANTES
    private List<ParticipanteDTO> participantes = new ArrayList<>();

    // INICIATIVAS
    private List<IniciativaDTO> iniciativa = new ArrayList<>();
}
