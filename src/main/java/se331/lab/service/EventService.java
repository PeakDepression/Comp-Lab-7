package se331.lab.service;

import org.springframework.data.domain.Page;
import se331.lab.entity.Event;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Integer getEventSize();
    Page<Event> getEvents(Integer pageSize, Integer page);
    Event getEvent(Long id);
    Event save(Event event);

    Page<Event> getEvents(String title, Pageable pageable);
}