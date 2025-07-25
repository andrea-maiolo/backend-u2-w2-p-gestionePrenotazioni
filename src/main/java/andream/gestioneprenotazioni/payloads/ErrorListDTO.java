package andream.gestioneprenotazioni.payloads;

import java.time.LocalDate;
import java.util.List;

public record ErrorListDTO(String message, LocalDate timestamp, List<String> errorList) {
}
