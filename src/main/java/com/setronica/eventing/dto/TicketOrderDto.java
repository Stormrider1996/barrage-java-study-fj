package com.setronica.eventing.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketOrderDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private Integer amount;
}
