package com.setronica.eventing.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "event_schedule")
public class EventSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Integer eventId;
    @Column(nullable = false)
    private LocalDate eventDate;
    @Column(nullable = false)
    private Integer availableSeats;
    @Column(nullable = false)
    private BigDecimal price;
}
