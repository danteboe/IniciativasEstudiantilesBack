package co.edu.uniandes.dse.iniciativasestudiantiles.dto;

import java.util.*;

import lombok.Data;

@Data
public class IniciativaDetailDTO extends IniciativaDTO
{
    // EVENTOS
    private List<EventoDTO> eventos = new ArrayList<>();

    // CONCURSO
    private List<ConcursoDTO> concursos = new ArrayList<>() ;

    // MIEMBRO
    private List<MiembroDTO> miembros = new ArrayList<>();
}
