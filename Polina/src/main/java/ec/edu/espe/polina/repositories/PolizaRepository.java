package ec.edu.espe.poliza.repositories;

import ec.edu.espe.poliza.models.entities.Poliza;
import org.springframework.data.repository.CrudRepository;

public interface PolizaRepository extends CrudRepository<Poliza, Long> {
}