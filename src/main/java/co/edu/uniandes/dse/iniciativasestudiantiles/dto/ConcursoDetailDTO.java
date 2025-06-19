package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import lombok.Data;
import java.util.*;

@Data
public class ConcursoDetailDTO extends ConcursoDTO
 {
    private List<IniciativaDTO> iniciativas = new ArrayList<>();


}
