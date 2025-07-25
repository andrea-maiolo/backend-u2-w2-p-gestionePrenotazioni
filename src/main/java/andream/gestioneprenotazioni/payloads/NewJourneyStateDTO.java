package andream.gestioneprenotazioni.payloads;

import andream.gestioneprenotazioni.enums.JourneyState;
import jakarta.validation.constraints.NotNull;

public record NewJourneyStateDTO(
        @NotNull(message = "state must be inserted")
        JourneyState state
) {
}
