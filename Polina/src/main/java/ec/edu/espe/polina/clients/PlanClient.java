package ec.edu.espe.poliza.clients;

import ec.edu.espe.poliza.models.dto.PlanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "plan-ms", url = "${ms.planes.url}/api/planes")
public interface PlanClient {
    @GetMapping("/{id}")
    Optional<PlanDTO> findById(@PathVariable Long id);
}