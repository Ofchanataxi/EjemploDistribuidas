package ec.edu.espe.autorms.repositories;

import ec.edu.espe.autorms.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}