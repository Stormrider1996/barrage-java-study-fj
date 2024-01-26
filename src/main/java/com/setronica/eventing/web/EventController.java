package com.setronica.eventing.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> findAll() {
        return eventService.getAll();
    }

    @GetMapping("search")
    public List<Event> searchEvents(
            @RequestParam String q) {
        return eventService.getByEvent(q);
    }
}
