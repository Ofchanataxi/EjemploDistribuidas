package ec.edu.espe.poliza.clients;

import ec.edu.espe.poliza.models.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "cliente-ms", url = "${ms.clientes.url}/api/clientes")
public interface ClienteClient {
    @GetMapping("/{id}")
    Optional<ClienteDTO> findById(@PathVariable Long id);
}