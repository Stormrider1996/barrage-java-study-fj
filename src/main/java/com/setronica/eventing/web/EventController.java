package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.dto.EventDto;
import com.setronica.eventing.mapper.EventMapper;
import com.setronica.eventing.persistence.Event;
import org.hibernate.event.internal.EvictVisitor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventDto> getAll() {
        List<Event> entities = eventService.getAll();
        return eventMapper.mapToDtoList(entities);
    }

    @GetMapping("{id}")
    public EventDto getById(@PathVariable Integer id) {
        Event entity = eventService.getById(id);
        return eventMapper.mapToDto(entity);
    }

    @PostMapping()
    public EventDto createEvent(@RequestBody EventDto dto) {
        Event event = eventMapper.mapToEvent(dto);
        Event createdEvent = eventService.createEvent(event);
        return eventMapper.mapToDto(createdEvent);
    }

    @PutMapping("{id}")
    public EventDto updateEvent(@RequestBody EventDto dto) {
        Event event = eventMapper.mapToEvent(dto);
        Event createdEvent = eventService.updateEvent(event);
        return eventMapper.mapToDto(createdEvent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}
