package ec.edu.espe.planms.controllers;

import ec.edu.espe.planms.models.entities.PlanSeguro;
import ec.edu.espe.planms.services.PlanSeguroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
public class PlanSeguroController {

    @Autowired
    private PlanSeguroService service;

    @GetMapping
    public ResponseEntity<List<PlanSeguro>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<PlanSeguro> planOptional = service.findById(id);
        if (planOptional.isPresent()) {
            return ResponseEntity.ok(planOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PlanSeguro> create(@Valid @RequestBody PlanSeguro planSeguro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(planSeguro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanSeguro> update(@PathVariable Long id, @Valid @RequestBody PlanSeguro planSeguro) {
        Optional<PlanSeguro> existingPlan = service.findById(id);
        if (existingPlan.isPresent()) {
            PlanSeguro planToUpdate = existingPlan.get();
            planToUpdate.setNombre(planSeguro.getNombre());
            planToUpdate.setTipo(planSeguro.getTipo());
            planToUpdate.setPrimaBase(planSeguro.getPrimaBase());
            planToUpdate.setCoberturaMax(planSeguro.getCoberturaMax());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(planToUpdate));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<PlanSeguro> planOptional = service.findById(id);
        if (planOptional.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}