package com.setronica.eventing.app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Integer id, Event event) {
        Event existingEvent = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDate(event.getDate());
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }
}