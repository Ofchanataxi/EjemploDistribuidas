package ec.edu.espe.test.test.services;

import ec.edu.espe.test.test.models.entities.PlanSeguro;
import ec.edu.espe.test.test.repositories.PlanSeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlanSeguroServiceImpl implements PlanSeguroService {

    @Autowired
    private PlanSeguroRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PlanSeguro> findAll() {
        return (List<PlanSeguro>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanSeguro> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public PlanSeguro save(PlanSeguro planSeguro) {
        return repository.save(planSeguro);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}