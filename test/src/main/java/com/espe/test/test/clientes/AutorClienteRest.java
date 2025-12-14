package com.espe.test.test.clientes;

import com.espe.test.test.models.AutorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "Autor", url = "http://autores:8080/api/autores")
public interface AutorClienteRest {
    @GetMapping("/{id}")
    Optional<AutorDTO> buscarPorId(@PathVariable Long id);
}
