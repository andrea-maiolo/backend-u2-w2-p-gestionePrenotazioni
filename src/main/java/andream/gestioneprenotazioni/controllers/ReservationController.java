package andream.gestioneprenotazioni.controllers;

import andream.gestioneprenotazioni.entities.Reservation;
import andream.gestioneprenotazioni.exceptions.ValidationException;
import andream.gestioneprenotazioni.payloads.NewReservationDTO;
import andream.gestioneprenotazioni.payloads.NewReservationResponseDTO;
import andream.gestioneprenotazioni.services.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationsService reservationService;

    @GetMapping
    public Page<Reservation> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.reservationService.getAll(pageNumber, pageSize, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewReservationResponseDTO create(@RequestBody @Validated NewReservationDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fe -> fe.getDefaultMessage())
                    .toList()
            );
        }
        Reservation newReservation = reservationService.save(payload);
        return new NewReservationResponseDTO(newReservation.getId());
    }

    @GetMapping("/{reservationId}")
    public Reservation getById(@PathVariable UUID reservationId) {
        return this.reservationService.getByID(reservationId);
    }

//    @PutMapping("/{employeeId}")
//    public Employee updateUser(@PathVariable UUID employeeId, @RequestBody NewEmployeeDTO payload) {
//        return this.employeesService.findAndUpdate(payload, employeeId);
//    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable UUID reservationId) {
        this.reservationService.findAndDelete(reservationId);
    }
}

