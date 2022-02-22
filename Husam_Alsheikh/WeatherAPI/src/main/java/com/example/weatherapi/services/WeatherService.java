package com.example.weatherapi.services;

import com.example.weatherapi.dao.WeatherRepo;
import com.example.weatherapi.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final WeatherRepo weatherRepo;

    @Autowired
    public WeatherService(WeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
    }

    public WeatherDTO getCurrentWeather(String location){
        //  Convert location if zip-code

        //  Return null if no weather data on location
        return null;
    }
}
