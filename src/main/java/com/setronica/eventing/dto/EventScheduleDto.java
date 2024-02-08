package com.setronica.eventing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EventScheduleDto {
    private int id;
    private int eventId;
    private LocalDate eventDate;
    private int availableSeats;
    private double price;
}
