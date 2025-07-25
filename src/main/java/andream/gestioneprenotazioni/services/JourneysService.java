package andream.gestioneprenotazioni.services;

import andream.gestioneprenotazioni.entities.Journey;
import andream.gestioneprenotazioni.enums.JourneyState;
import andream.gestioneprenotazioni.exceptions.NotFoundException;
import andream.gestioneprenotazioni.payloads.NewJourneyDTO;
import andream.gestioneprenotazioni.repositories.JourneyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
