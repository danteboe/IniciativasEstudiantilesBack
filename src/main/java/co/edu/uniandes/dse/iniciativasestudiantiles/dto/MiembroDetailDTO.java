package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class MiembroDetailDTO extends MiembroDTO {
    private List<IniciativaDTO> iniciativas= new ArrayList<>();
}
