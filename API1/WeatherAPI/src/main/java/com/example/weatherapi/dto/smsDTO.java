package com.example.weatherapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class smsDTO {
    private String location;
    private String weatherDTO;

    @Override
    public String toString() {
        return "Weather from the city of " + location +
                "\n" + weatherDTO;
    }
}
