package andream.gestioneprenotazioni.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<String> errorList;

    public ValidationException(List<String> errorList) {
        super("validation errors");
        this.errorList = errorList;
    }
}
