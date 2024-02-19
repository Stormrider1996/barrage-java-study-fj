package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.dto.EventScheduleDto;
import com.setronica.eventing.mapper.EventScheduleMapper;
import com.setronica.eventing.persistence.EventSchedule;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/event-schedules")
public class EventScheduleController {

    private final EventScheduleService eventScheduleService;

    private final EventScheduleMapper eventScheduleMapper;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventScheduleController.class);

    public EventScheduleController(EventScheduleService eventScheduleService, EventScheduleMapper eventScheduleMapper) {
        this.eventScheduleService = eventScheduleService;
        this.eventScheduleMapper = eventScheduleMapper;
    }

    @GetMapping
    public List<EventSchedule> findAll() {
        return eventScheduleService.getAll();
    }

    @GetMapping("/{id}")
    public  EventScheduleDto getById(@PathVariable("id") Integer id) {
        EventSchedule eventSchedule = eventScheduleService.getById(id);
        return eventScheduleMapper.mapToDto(eventSchedule);
    }

    @PostMapping("")
    public EventScheduleDto createEventSchedule(@RequestBody EventScheduleDto dto) {
        EventSchedule eventSchedule = eventScheduleMapper.mapToEventSchedule(dto);
        EventSchedule createdEventSchedule = eventScheduleService.createEventSchedule(eventSchedule);
        return eventScheduleMapper.mapToDto(createdEventSchedule);
    }

    @PutMapping("/{id}")
    public EventScheduleDto updateEventSchedule(@RequestBody EventScheduleDto dto, @PathVariable("id") Integer id) {
        EventSchedule eventSchedule = eventScheduleMapper.mapToEventSchedule(dto);
        EventSchedule updatedEventSchedule = eventScheduleService.updateEventSchedule(eventSchedule, id);
        return eventScheduleMapper.mapToDto(updatedEventSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventSchedule(@PathVariable("id") int id) {
        eventScheduleService.deleteEventSchedule(id);
        return ResponseEntity.ok().build();
    }
}
