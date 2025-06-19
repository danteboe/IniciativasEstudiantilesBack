package co.edu.uniandes.dse.iniciativasestudiantiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import co.edu.uniandes.dse.iniciativasestudiantiles.entities.MiembroEntity;

@Repository
public interface MiembroRepository extends JpaRepository<MiembroEntity, Long>  {

}
