package andream.gestioneprenotazioni.repositories;

import andream.gestioneprenotazioni.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, UUID> {
}
