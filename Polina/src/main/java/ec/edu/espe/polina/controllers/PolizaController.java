package ec.edu.espe.polizams.controllers;

import ec.edu.espe.polizams.clients.ClienteClient;
import ec.edu.espe.polizams.clients.PlanClient;
import ec.edu.espe.polizams.models.dto.ClienteDTO;
import ec.edu.espe.polizams.models.dto.PlanDTO;
import ec.edu.espe.polizams.models.entities.Poliza;
import ec.edu.espe.polizams.services.PolizaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/polizas")
public class PolizaController {

    @Autowired
    private PolizaService service;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private PlanClient planClient;

    @GetMapping
    public ResponseEntity<List<Poliza>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Poliza> polizaOptional = service.findById(id);
        if (polizaOptional.isPresent()) {
            return ResponseEntity.ok(polizaOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Poliza poliza) {
        // Validación con Feign
        Optional<ClienteDTO> cliente = clienteClient.findById(poliza.getClienteId());
        if (cliente.isEmpty()) {
            return ResponseEntity.badRequest().body("El Cliente con ID " + poliza.getClienteId() + " no existe.");
        }

        Optional<PlanDTO> plan = planClient.findById(poliza.getPlanId());
        if (plan.isEmpty()) {
            return ResponseEntity.badRequest().body("El Plan con ID " + poliza.getPlanId() + " no existe.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(poliza));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Poliza poliza) {
        Optional<Poliza> existingPoliza = service.findById(id);
        if (existingPoliza.isPresent()) {
            // Validar dependencias si cambian
            Optional<ClienteDTO> cliente = clienteClient.findById(poliza.getClienteId());
            if (cliente.isEmpty()) {
                return ResponseEntity.badRequest().body("El Cliente no existe.");
            }
            Optional<PlanDTO> plan = planClient.findById(poliza.getPlanId());
            if (plan.isEmpty()) {
                return ResponseEntity.badRequest().body("El Plan no existe.");
            }

            Poliza polizaToUpdate = existingPoliza.get();
            polizaToUpdate.setNumeroPoliza(poliza.getNumeroPoliza());
            polizaToUpdate.setFechaInicio(poliza.getFechaInicio());
            polizaToUpdate.setFechaFin(poliza.getFechaFin());
            polizaToUpdate.setPrimaMensual(poliza.getPrimaMensual());
            polizaToUpdate.setEstado(poliza.getEstado());
            polizaToUpdate.setClienteId(poliza.getClienteId());
            polizaToUpdate.setPlanId(poliza.getPlanId());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(polizaToUpdate));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Poliza> polizaOptional = service.findById(id);
        if (polizaOptional.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}