package com.example.act4.controller;

import com.example.act4.dto.CancionRequest;
import com.example.act4.dto.CancionResponse;
import com.example.act4.service.CancionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/canciones")
public class CancionController {

    private final CancionService service;

    public CancionController(CancionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PagedModel<CancionResponse>> listar(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(new PagedModel<>(service.listar(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancionResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<CancionResponse> crear(@Valid @RequestBody CancionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancionResponse> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody CancionRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}