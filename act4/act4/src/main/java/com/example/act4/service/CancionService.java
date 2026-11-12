package com.example.act4.service;

import com.example.act4.dto.CancionRequest;
import com.example.act4.dto.CancionResponse;
import com.example.act4.entity.Artista;
import com.example.act4.entity.Cancion;
import com.example.act4.repository.ArtistaRepository;
import com.example.act4.repository.CancionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CancionService {

    private final CancionRepository cancionRepo;
    private final ArtistaRepository artistaRepo;

    public CancionService(CancionRepository cancionRepo, ArtistaRepository artistaRepo) {
        this.cancionRepo = cancionRepo;
        this.artistaRepo = artistaRepo;
    }

    public Page<CancionResponse> listar(Pageable pageable) {
        return cancionRepo.findAll(pageable).map(this::aRespuesta);
    }

    public CancionResponse obtener(Long id) {
        return aRespuesta(buscar(id));
    }

    public CancionResponse crear(CancionRequest req) {
        Cancion c = new Cancion();
        llenar(c, req);
        return aRespuesta(cancionRepo.save(c));
    }

    public CancionResponse actualizar(Long id, CancionRequest req) {
        Cancion c = buscar(id);
        llenar(c, req);
        return aRespuesta(cancionRepo.save(c));
    }

    public void eliminar(Long id) {
        cancionRepo.delete(buscar(id));
    }

    private Cancion buscar(Long id) {
        return cancionRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Canción no encontrada"));
    }

    private void llenar(Cancion c, CancionRequest req) {
        // Si el artista no existe, se crea
        Artista artista = artistaRepo.findByNombre(req.artista()).orElseGet(() -> {
            Artista nuevo = new Artista();
            nuevo.setNombre(req.artista());
            return artistaRepo.save(nuevo);
        });
        c.setTitulo(req.titulo());
        c.setDuracion(req.duracion());
        c.setArtista(artista);
    }

    private CancionResponse aRespuesta(Cancion c) {
        return new CancionResponse(c.getId(), c.getTitulo(), c.getDuracion(), c.getArtista().getNombre());
    }
}