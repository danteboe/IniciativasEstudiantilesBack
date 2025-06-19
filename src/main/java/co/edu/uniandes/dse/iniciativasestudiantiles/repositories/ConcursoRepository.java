package co.edu.uniandes.dse.iniciativasestudiantiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import co.edu.uniandes.dse.iniciativasestudiantiles.entities.ConcursoEntity;
import java.util.List;

@Repository
public interface ConcursoRepository extends JpaRepository<ConcursoEntity, Long> {
    List<ConcursoEntity> findAllByOrderByTituloAsc();

}
