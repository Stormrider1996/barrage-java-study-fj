package com.setronica.eventing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class EventDto {
    private int id;
    private String title;
    private String description;
    private LocalDate date;
    private List<String> images;
}
