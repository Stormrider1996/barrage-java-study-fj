package com.setronica.eventing.web;

import com.setronica.eventing.app.EventScheduleService;
import com.setronica.eventing.dto.EventScheduleDto;
import com.setronica.eventing.mapper.EventScheduleMapper;
import com.setronica.eventing.persistence.EventSchedule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/event-schedules")
@Tag(name = "Event Schedule Controller")
public class EventScheduleController {

    private final EventScheduleService eventScheduleService;

    private final EventScheduleMapper eventScheduleMapper;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventScheduleController.class);

    public EventScheduleController(EventScheduleService eventScheduleService, EventScheduleMapper eventScheduleMapper) {
        this.eventScheduleService = eventScheduleService;
        this.eventScheduleMapper = eventScheduleMapper;
    }

    @Operation(summary = "Get all event schedules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the event schedules"),
            @ApiResponse(responseCode = "404", description = "Event schedules not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<EventSchedule> findAll() {
        return eventScheduleService.getAll();
    }

    @Operation(summary = "Get event schedule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the event schedule"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Event schedule not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public  EventScheduleDto getById(@PathVariable("id") Integer id) {
        EventSchedule eventSchedule = eventScheduleService.getById(id);
        return eventScheduleMapper.mapToDto(eventSchedule);
    }

    @Operation(summary = "Create a new event schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event schedule created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("")
    public EventScheduleDto createEventSchedule(@RequestBody EventScheduleDto dto) {
        EventSchedule eventSchedule = eventScheduleMapper.mapToEventSchedule(dto);
        EventSchedule createdEventSchedule = eventScheduleService.createEventSchedule(eventSchedule);
        return eventScheduleMapper.mapToDto(createdEventSchedule);
    }

    @Operation(summary = "Update an existing event schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event schedule updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Event schedule not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("")
    public EventScheduleDto updateEventSchedule(@RequestBody EventScheduleDto dto) {
        EventSchedule eventSchedule = eventScheduleMapper.mapToEventSchedule(dto);
        EventSchedule updatedEventSchedule = eventScheduleService.updateEventSchedule(eventSchedule);
        return eventScheduleMapper.mapToDto(updatedEventSchedule);
    }

    @Operation(summary = "Delete an event schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event schedule deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Event schedule not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventSchedule(@PathVariable("id") int id) {
        eventScheduleService.deleteEventSchedule(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/async")
    public void asyncOp() throws InterruptedException {
        eventScheduleService.asyncOperation();
    }
}