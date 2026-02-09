package ec.edu.espe.poliza.services;

import ec.edu.espe.poliza.models.entities.Poliza;
import java.util.List;
import java.util.Optional;

public interface PolizaService {
    List<Poliza> findAll();
    Optional<Poliza> findById(Long id);
    Poliza save(Poliza poliza);
    void delete(Long id);
}