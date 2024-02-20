package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.dto.EventDto;
import com.setronica.eventing.mapper.EventMapper;
import com.setronica.eventing.persistence.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.event.internal.EvictVisitor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
@Tag(name = "Event Controller")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @Operation(summary = "Get all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the events"),
            @ApiResponse(responseCode = "404", description = "Events not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<EventDto> getAll() {
        List<Event> entities = eventService.getAll();
        return eventMapper.mapToDtoList(entities);
    }

    @Operation(summary = "Get event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the event"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public EventDto getById(@PathVariable Integer id) {
        Event entity = eventService.getById(id);
        return eventMapper.mapToDto(entity);
    }

    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public EventDto createEvent(@RequestBody EventDto dto) {
        Event event = eventMapper.mapToEvent(dto);
        Event createdEvent = eventService.createEvent(event);
        return eventMapper.mapToDto(createdEvent);
    }

    @Operation(summary = "Update an existing event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("")
    public EventDto updateEvent(@RequestBody EventDto dto) {
        Event event = eventMapper.mapToEvent(dto);
        Event createdEvent = eventService.updateEvent(event);
        return eventMapper.mapToDto(createdEvent);
    }

    @Operation(summary = "Delete an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/async")
    public void asyncOp() throws InterruptedException {
        eventService.asyncOperation();
    }
}
