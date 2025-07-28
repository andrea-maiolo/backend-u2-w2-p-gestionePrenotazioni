package andream.gestioneprenotazioni.payloads;

import jakarta.validation.constraints.NotEmpty;

public record StateDTO(
        @NotEmpty(message = "new state is required")
        String state
) {
}
