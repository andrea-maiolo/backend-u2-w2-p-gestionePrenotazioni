package andream.gestioneprenotazioni.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record NewReservationDTO(
        @NotNull(message = "Request date is required")
        LocalDate requestDate,
        @Size(max = 40, message = "max size 40")
        String notes,
        @NotNull(message = "Employee ID is required")
        UUID employeeId,
        @NotNull(message = "Journey ID is required")
        UUID journeyId
) {
}
