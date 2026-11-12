package com.example.act4.repository;

import com.example.act4.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNombre(String nombre);
}