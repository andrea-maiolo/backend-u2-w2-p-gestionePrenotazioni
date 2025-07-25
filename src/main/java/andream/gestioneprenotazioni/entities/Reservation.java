package andream.gestioneprenotazioni.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate requestDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "employee_name")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "destination")
    private Journey destination;

    public Reservation() {
    }

    public Reservation(LocalDate requestDate, String notes, Employee employee, Journey destination) {
        this.requestDate = requestDate;
        this.notes = notes;
        this.employee = employee;
        this.destination = destination;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Journey getDestination() {
        return destination;
    }

    public void setDestination(Journey destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", notes='" + notes + '\'' +
                ", employee=" + employee.getName() +
                ", destination=" + destination.getDestination() +
                '}';
    }
}
