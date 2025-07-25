package andream.gestioneprenotazioni.repositories;

import andream.gestioneprenotazioni.entities.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JourneyRepo extends JpaRepository<Journey, UUID> {
}
