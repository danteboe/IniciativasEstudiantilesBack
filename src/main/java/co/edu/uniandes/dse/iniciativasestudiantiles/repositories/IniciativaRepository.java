package co.edu.uniandes.dse.iniciativasestudiantiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.iniciativasestudiantiles.entities.IniciativaEntity;

@Repository
public interface IniciativaRepository extends JpaRepository<IniciativaEntity, Long> {
}
