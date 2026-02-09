package ec.edu.espe.test.test.services;

import ec.edu.espe.test.test.models.entities.PlanSeguro;
import java.util.List;
import java.util.Optional;

public interface PlanSeguroService {
    List<PlanSeguro> findAll();
    Optional<PlanSeguro> findById(Long id);
    PlanSeguro save(PlanSeguro planSeguro);
    void delete(Long id);
}