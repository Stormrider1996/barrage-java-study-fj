package com.setronica.eventing.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaleDto {
    private String email;
    private String title;
    private double amount;

    public SaleDto(String email, String title, double amount) {
        this.email = email;
        this.title = title;
        this.amount = amount;
    }
}
