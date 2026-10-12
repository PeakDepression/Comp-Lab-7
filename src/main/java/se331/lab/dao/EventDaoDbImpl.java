package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import se331.lab.entity.Event;
import se331.lab.repository.EventRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class EventDaoDbImpl implements EventDao {
    final EventRepository eventRepository;

    @Override
    public Integer getEventSize() {
        return Math.toIntExact(eventRepository.count());
    }

    @Override
    public Page<Event> getEvents(Integer pageSize, Integer page) {
        pageSize = pageSize == null ? 2 : pageSize;
        page = page == null ? 1 : page;
        return eventRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}