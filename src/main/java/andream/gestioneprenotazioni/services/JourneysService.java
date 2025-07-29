package andream.gestioneprenotazioni.services;

import andream.gestioneprenotazioni.entities.Journey;
import andream.gestioneprenotazioni.enums.JourneyState;
import andream.gestioneprenotazioni.exceptions.BadRequestException;
import andream.gestioneprenotazioni.exceptions.NotFoundException;
import andream.gestioneprenotazioni.payloads.NewJourneyDTO;
import andream.gestioneprenotazioni.payloads.StateDTO;
import andream.gestioneprenotazioni.repositories.JourneyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JourneysService {
    @Autowired
    private JourneyRepo journeyRepo;

    public Journey save(NewJourneyDTO payload) {
        Journey newJourney = new Journey(payload.startDate(), payload.destination(), JourneyState.PLANNED);
        this.journeyRepo.save(newJourney);
        return newJourney;
    }

    public Page<Journey> getAll(int pageNumber, int pageSize, String sort) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort).ascending());
        return this.journeyRepo.findAll(pageable);
    }

    public Journey getByID(UUID jId) {
        return this.journeyRepo.findById(jId).orElseThrow(() -> new NotFoundException("journey not found"));
    }

    public Journey findAndUpdate(NewJourneyDTO payload, UUID journeyId) {
        Journey found = this.getByID(journeyId);
        found.setDestination(payload.destination());
        found.setStartDate(payload.startDate());
        found.setState(found.getState());
        this.journeyRepo.save(found);
        return found;
    }

    public void findAndDelete(UUID journeyId) {
        Journey found = this.journeyRepo.findById(journeyId).orElseThrow(() -> new NotFoundException("user not found"));
        this.journeyRepo.delete(found);
    }

//    public Journey updateJourneyState(StateDTO payload, UUID journeyId) {
//        Journey found = this.journeyRepo.getById(journeyId);
//        System.out.println("=====================now==========");
//        System.out.println(JourneyState.valueOf(payload.state().toUpperCase()));
//        try {
//            JourneyState newState = JourneyState.valueOf(payload.state().toUpperCase());
//            found.setState(newState);
//            this.journeyRepo.save(found);
//            return found;
//        } catch (IllegalArgumentException ex) {
//            // More specific error message with valid states
//            String validStates = Arrays.stream(JourneyState.values())
//                    .map(Enum::name)
//                    .collect(Collectors.joining(", "));
//            throw new BadRequestException("Invalid state '" + payload.state() +
//                    "'. Valid states are: " + validStates);
//        }
//    }


    public Journey updateJourneyState(StateDTO payload, UUID journeyId) {
        Journey found = this.journeyRepo.getById(journeyId);

        try {
            String stateString = payload.state().toUpperCase();
            JourneyState newState = JourneyState.valueOf(stateString);
            found.setState(newState);
            Journey saved = this.journeyRepo.save(found);
            return saved;

        } catch (IllegalArgumentException ex) {
            String validStates = Arrays.stream(JourneyState.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            System.out.println("Invalid state error. Input: " + payload.state() + ", Valid states: " + validStates);
            throw new BadRequestException("Invalid state '" + payload.state() +
                    "'. Valid states are: " + validStates);
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
    }

}
