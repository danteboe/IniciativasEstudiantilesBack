package co.edu.uniandes.dse.iniciativasestudiantiles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.EventoEntity;

@Repository
public interface EventoRepository  extends JpaRepository<EventoEntity, Long> {
    List<EventoEntity> findAllByOrderByTituloAsc();
}
