package com.example.act4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CancionRequest(
        @NotBlank(message = "El título es obligatorio") String titulo,
        @NotNull(message = "La duración es obligatoria") @Positive(message = "La duración debe ser mayor a 0") Integer duracion,
        @NotBlank(message = "El artista es obligatorio") String artista) {
}