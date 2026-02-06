package ec.edu.espe.polizams.repositories;

import ec.edu.espe.polizams.models.entities.Poliza;
import org.springframework.data.repository.CrudRepository;

public interface PolizaRepository extends CrudRepository<Poliza, Long> {
}