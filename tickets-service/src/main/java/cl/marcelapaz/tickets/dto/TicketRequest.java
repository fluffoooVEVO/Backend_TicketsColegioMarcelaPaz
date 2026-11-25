package cl.marcelapaz.tickets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TicketRequest(
        @NotNull Long usuarioId,
        @NotBlank @Size(min = 5, max = 100) String titulo,
        @NotBlank @Size(min = 10, max = 1000) String descripcion,
        @NotBlank @Size(max = 100) String sector,
        @NotBlank @Pattern(regexp = "Baja|Media|Alta") String urgencia
) {
}