package andream.gestioneprenotazioni.services;


import andream.gestioneprenotazioni.entities.Employee;
import andream.gestioneprenotazioni.entities.Journey;
import andream.gestioneprenotazioni.entities.Reservation;
import andream.gestioneprenotazioni.exceptions.BadRequestException;
import andream.gestioneprenotazioni.exceptions.NotFoundException;
import andream.gestioneprenotazioni.payloads.NewReservationDTO;
import andream.gestioneprenotazioni.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationsService {
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private JourneysService journeysService;
    @Autowired
    private EmployeesService employeesService;

    public Reservation save(NewReservationDTO payload) {
        boolean employeeAlreadyBusy = this.reservationRepo.findByEmployeeNameAndRequestDate(payload.employeeId(), payload.requestDate()).isPresent();

        if (employeeAlreadyBusy) {
            throw new BadRequestException("employee already as a booked journey for this date");
        }

        Employee resEm = this.employeesService.getByID(payload.employeeId());
        Journey resJ = this.journeysService.getByID(payload.journeyId());

        Reservation newR = new Reservation(payload.requestDate(), payload.notes(), resEm, resJ);
        this.reservationRepo.save(newR);
        return newR;
    }


    public Page<Reservation> getAll(int pageNumber, int pageSize, String sort) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort).ascending());
        return this.reservationRepo.findAll(pageable);
    }


    public Reservation getByID(UUID reservationId) {
        return this.reservationRepo.findById(reservationId).orElseThrow(() -> new NotFoundException("reservation not found"));
    }

    //put da fare

    public void findAndDelete(UUID reservationId) {
        Reservation found = this.reservationRepo.findById(reservationId).orElseThrow(() -> new NotFoundException("reservation not found"));
        this.reservationRepo.delete(found);
    }


}
