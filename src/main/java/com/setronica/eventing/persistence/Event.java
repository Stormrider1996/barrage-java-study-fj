package com.setronica.eventing.persistence;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "\"event\"")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private LocalDate date;

    @Type(JsonType.class)
    @Column
    private List<String> images;
}
