package com.espe.test.test.controllers;

import com.espe.test.test.clientes.AutorClienteRest;
import com.espe.test.test.models.AutorDTO;
import com.espe.test.test.models.entities.Libro;
import com.espe.test.test.services.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @Autowired
    private AutorClienteRest autorClienteRest;

    // GET /api/libros -> Listar todos
    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    // 2. NUEVO: GET /api/libros/{id} -> Buscar uno solo
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.buscarPorId(id);
        if (libroOptional.isPresent()) {
            return ResponseEntity.ok(libroOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/libros -> Crear
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Libro libro) {
        Optional<AutorDTO> autorDTO = autorClienteRest.buscarPorId(libro.getAutor_id());
        if (autorDTO.isPresent()) {
            Libro libroDB = service.guardar(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroDB);
        }
        return ResponseEntity.badRequest().build();
    }

    // PUT /api/libros/{id} -> Editar
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Libro libro, @PathVariable Long id) {
        Optional<Libro> libroOptional = service.buscarPorId(id);

        if (libroOptional.isPresent()) {
            Libro libroDB = libroOptional.get();
            libroDB.setTitulo(libro.getTitulo());
            libroDB.setAutor_id(libro.getAutor_id());
            libroDB.setGenero(libro.getGenero());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(libroDB));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/libros/{id} -> Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.buscarPorId(id);
        if (libroOptional.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}