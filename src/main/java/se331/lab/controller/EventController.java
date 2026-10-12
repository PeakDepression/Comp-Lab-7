package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.lab.entity.Event;
import se331.lab.service.EventService;
import se331.lab.util.LabMapper;

@CrossOrigin(origins = "http://localhost:5173", exposedHeaders = "x-total-count")
@RestController
@RequiredArgsConstructor
public class EventController {

    final EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<?> getEventLists(
            @RequestParam(value = "_limit", required = false) Integer limit,
            @RequestParam(value = "_per_page", required = false) Integer perPageParam,
            @RequestParam(value = "_page", required = false, defaultValue = "1") Integer page) {

        Integer pageSize = perPageParam != null ? perPageParam : (limit != null ? limit : 3);
        Page<Event> pageOutput = eventService.getEvents(pageSize, page);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(LabMapper.INSTANCE.getEventDto(pageOutput.getContent()));
    }

    @GetMapping("events/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        Event output = eventService.getEvent(id);
        if (output != null) {
            return ResponseEntity.ok(LabMapper.INSTANCE.getEventDto(output));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }

    @PostMapping("/events")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        Event savedEvent = eventService.save(event);
        return ResponseEntity.ok(LabMapper.INSTANCE.getEventDto(savedEvent));
    }
}