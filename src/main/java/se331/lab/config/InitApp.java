package se331.lab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import se331.lab.entity.Event;
import se331.lab.entity.Organizer;
import se331.lab.entity.Participant;
import se331.lab.repository.EventRepository;
import se331.lab.repository.OrganizerRepository;
import se331.lab.repository.ParticipantRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;
    final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Organizer org1 = organizerRepository.save(Organizer.builder().name("CAMT").build());
        Organizer org2 = organizerRepository.save(Organizer.builder().name("CMU").build());
        Organizer org3 = organizerRepository.save(Organizer.builder().name("ChiangMai").build());

        Event event1 = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Midterm Exam")
                .description("A time for taking the exam")
                .location("CAMT Building")
                .date("3rd Sept")
                .time("3.00-4.00 pm.")
                .petsAllowed(false)
                .build());
        event1.setOrganizer(org1);
        org1.getOwnEvents().add(event1);

        Event event2 = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Commencement Day")
                .description("A time for celebration")
                .location("CMU Convention hall")
                .date("21th Jan")
                .time("8.00am-4.00 pm.")
                .petsAllowed(false)
                .build());
        event2.setOrganizer(org1);
        org1.getOwnEvents().add(event2);

        Event event3 = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Loy Krathong")
                .description("A time for Krathong")
                .location("Ping River")
                .date("21th Nov")
                .time("8.00-10.00 pm.")
                .petsAllowed(false)
                .build());
        event3.setOrganizer(org2);
        org2.getOwnEvents().add(event3);

        Event event4 = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Songkran")
                .description("Let's Play Water")
                .location("Chiang Mai Moat")
                .date("13th April")
                .time("10.00am - 6.00 pm.")
                .petsAllowed(true)
                .build());
        event4.setOrganizer(org3);
        org3.getOwnEvents().add(event4);

        // --- Task 7.1: Create 5 participants ---
        Participant p1 = Participant.builder().name("Somchai").telNo("081-1111111").build();
        Participant p2 = Participant.builder().name("Somsak").telNo("082-2222222").build();
        Participant p3 = Participant.builder().name("Somying").telNo("083-3333333").build();
        Participant p4 = Participant.builder().name("Ananda").telNo("084-4444444").build();
        Participant p5 = Participant.builder().name("Kanda").telNo("085-5555555").build();

        participantRepository.save(p1);
        participantRepository.save(p2);
        participantRepository.save(p3);
        participantRepository.save(p4);
        participantRepository.save(p5);

        // p1, p2, p3 attend exactly 3 events
        p1.getEventHistories().add(event1);
        p1.getEventHistories().add(event2);
        p1.getEventHistories().add(event3);

        p2.getEventHistories().add(event1);
        p2.getEventHistories().add(event2);
        p2.getEventHistories().add(event4);

        p3.getEventHistories().add(event1);
        p3.getEventHistories().add(event3);
        p3.getEventHistories().add(event4);

        // p4 and p5 attend all 4 events
        p4.getEventHistories().add(event1);
        p4.getEventHistories().add(event2);
        p4.getEventHistories().add(event3);
        p4.getEventHistories().add(event4);

        p5.getEventHistories().add(event1);
        p5.getEventHistories().add(event2);
        p5.getEventHistories().add(event3);
        p5.getEventHistories().add(event4);

        // Sync bidirectional collections on events
        event1.getParticipants().addAll(List.of(p1, p2, p3, p4, p5)); // 5 participants
        event2.getParticipants().addAll(List.of(p1, p2, p4, p5));     // 4 participants
        event3.getParticipants().addAll(List.of(p1, p3, p4, p5));     // 4 participants
        event4.getParticipants().addAll(List.of(p2, p3, p4, p5));     // 4 participants

        participantRepository.saveAll(List.of(p1, p2, p3, p4, p5));
        eventRepository.saveAll(List.of(event1, event2, event3, event4));
    }
}