package co.edu.uniandes.dse.iniciativasestudiantiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ComentarioEntity;

import java.util.List;

@Repository
public interface ComentarioRepository  extends JpaRepository<ComentarioEntity, Long> {
    List<ComentarioEntity> findAllByOrderByFechaDesc();
}