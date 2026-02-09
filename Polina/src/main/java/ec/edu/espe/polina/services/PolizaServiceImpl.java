package ec.edu.espe.poliza.services;

import ec.edu.espe.poliza.models.entities.Poliza;
import ec.edu.espe.poliza.repositories.PolizaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PolizaServiceImpl implements PolizaService {

    @Autowired
    private PolizaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Poliza> findAll() {
        return (List<Poliza>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Poliza> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Poliza save(Poliza poliza) {
        return repository.save(poliza);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}