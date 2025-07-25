package andream.gestioneprenotazioni.services;

import andream.gestioneprenotazioni.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationsService {
    @Autowired
    private ReservationRepo reservationRepo;
}
