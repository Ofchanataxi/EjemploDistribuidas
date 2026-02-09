package ec.edu.espe.polina.services;

import ec.edu.espe.polina.models.entities.Poliza;
import java.util.List;
import java.util.Optional;

public interface PolizaService {
    List<Poliza> findAll();
    Optional<Poliza> findById(Long id);
    Poliza save(Poliza poliza);
    void delete(Long id);
}