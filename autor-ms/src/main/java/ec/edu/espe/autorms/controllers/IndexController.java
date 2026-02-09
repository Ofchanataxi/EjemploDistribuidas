package ec.edu.espe.autorms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "app", "clientes-ms");
    }
}
