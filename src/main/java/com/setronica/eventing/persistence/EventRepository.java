package com.setronica.eventing.persistence;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventRepository {

    private final ObjectMapper objectMapper;

    public EventRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Event> findAll() {
        try (InputStream inputStream = EventRepository.class.getResourceAsStream("/static/events.json")) {
            if (inputStream != null) {
                return objectMapper.readValue(inputStream, EventList.class);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Event> getByEvent(String q) {
        List<Event> events = findAll();

        return events.stream().filter(e -> e.getTitle().equalsIgnoreCase(q)).collect(Collectors.toList());
    }
}
