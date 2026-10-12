package se331.lab.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String category;
    String title;
    String description;
    String location;
    String date;
    String time;
    Boolean petsAllowed;

    @ManyToOne
    Organizer organizer;

    @ManyToMany(mappedBy = "eventHistories")
    List<Participant> participants;
}