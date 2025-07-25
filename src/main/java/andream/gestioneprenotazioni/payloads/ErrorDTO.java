package andream.gestioneprenotazioni.payloads;

import java.time.LocalDate;

public record ErrorDTO(String message, LocalDate timestamp) {
}
