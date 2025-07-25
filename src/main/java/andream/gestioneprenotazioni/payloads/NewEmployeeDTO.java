package andream.gestioneprenotazioni.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(
        @NotEmpty(message = "name is required")
        @Size(min = 2, max = 15, message = "name must be shorter than 2 and no longer than 15 characters")
        String name,
        @NotEmpty(message = "surname is required")
        @Size(min = 2, max = 15, message = "surname must be shorter than 2 and no longer than 15 characters")
        String surname,
        @NotEmpty(message = "email is required")
        @Email(message = "not right format for email")
        String email,
        @NotEmpty(message = "username is required")
        @Size(min = 2, max = 15, message = "username must be shorter than 2 and no longer than 15 characters")
        String username
) {
}