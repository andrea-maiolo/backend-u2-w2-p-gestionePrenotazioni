package andream.gestioneprenotazioni.controllers;


import andream.gestioneprenotazioni.entities.Journey;
import andream.gestioneprenotazioni.exceptions.BadRequestException;
import andream.gestioneprenotazioni.exceptions.ValidationException;
import andream.gestioneprenotazioni.payloads.NewJourneyDTO;
import andream.gestioneprenotazioni.payloads.NewJourneyResponseDTO;
import andream.gestioneprenotazioni.payloads.StateDTO;
import andream.gestioneprenotazioni.services.JourneysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/journeys")
public class JourneyController {
    @Autowired
    private JourneysService journeysService;

    @GetMapping
    public Page<Journey> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.journeysService.getAll(pageNumber, pageSize, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewJourneyResponseDTO save(@RequestBody @Validated NewJourneyDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fe -> fe.getDefaultMessage())
                    .toList()
            );
        } else {
            Journey newJ = this.journeysService.save(payload);
            return new NewJourneyResponseDTO(newJ.getId());
        }
    }

    @GetMapping("/{journeyId}")
    public Journey getById(@PathVariable UUID journeyId) {
        return this.journeysService.getByID(journeyId);
    }

    @PutMapping("/{journeyId}")
    public Journey updateJourney(@PathVariable UUID journeyId, @RequestBody NewJourneyDTO payload) {
        return this.journeysService.findAndUpdate(payload, journeyId);
    }

    @DeleteMapping("/{journeyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJourney(@PathVariable UUID journeyId) {
        this.journeysService.findAndDelete(journeyId);
    }


    @PatchMapping("/{journeyId}/state")
    public Journey statusUpdate(@PathVariable UUID journeyId, @RequestBody @Validated StateDTO payload,
                                BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new BadRequestException("something went wrong");
        try {
            return this.journeysService.updateJourneyState(payload, journeyId);
        } catch (Exception e) {
            System.out.println("Error in controller: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
