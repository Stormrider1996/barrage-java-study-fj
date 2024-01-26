package com.setronica.eventing.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public List<Event> getByEvent(String q) {
        return eventRepository.getByEvent(q);
    }
}
