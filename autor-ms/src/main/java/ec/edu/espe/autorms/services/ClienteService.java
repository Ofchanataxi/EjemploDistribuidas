package ec.edu.espe.autorms.services;

import ec.edu.espe.autorms.models.entities.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Cliente save(Cliente cliente);
    void delete(Long id);
}