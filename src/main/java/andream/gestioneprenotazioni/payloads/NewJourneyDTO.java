package andream.gestioneprenotazioni.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewJourneyDTO(
        @NotNull(message = "Destination is required")
        @Size(min = 2, max = 100, message = "Destination must be between 2 and 100 characters")
        String destination,
        @NotNull(message = "Start date is required")
        LocalDate startDate
) {
}

