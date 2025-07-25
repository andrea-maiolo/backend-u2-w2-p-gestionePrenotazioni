package andream.gestioneprenotazioni.entities;

import andream.gestioneprenotazioni.enums.JourneyState;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "journeys")
public class Journey {
    @Id
    @GeneratedValue
    private UUID id;
    private String destination;
    private LocalDate startDate;
    @Enumerated(EnumType.STRING)
    private JourneyState state;

    public Journey() {
    }

    public Journey(LocalDate startDate, String destination, JourneyState state) {
        this.startDate = startDate;
        this.destination = destination;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public JourneyState getState() {
        return state;
    }

    public void setState(JourneyState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", state=" + state +
                '}';
    }
}
