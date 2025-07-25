package andream.gestioneprenotazioni.repositories;

import andream.gestioneprenotazioni.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, UUID> {

    @Query(value = "SELECT * FROM reservations WHERE employee_name = :employeeId AND request_date = :requestDate",
            nativeQuery = true)
    Optional<Reservation> findByEmployeeNameAndRequestDate(@Param("employeeId") UUID employeeId,
                                                           @Param("requestDate") LocalDate requestDate);

}
