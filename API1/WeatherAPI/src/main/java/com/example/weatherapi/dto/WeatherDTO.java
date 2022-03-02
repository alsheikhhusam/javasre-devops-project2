package com.example.weatherapi.dto;

import com.example.weatherapi.models.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private String date;
    private Double temp;
    private Double feels_like;
    private Double pressure;
    private Double humidity;
    private Double wind_speed;
    private String description;

    public WeatherDTO(Weather weather){
        this.date = weather.getWeatherDate().toString();
        this.temp = weather.getTemperature();
        this.feels_like = weather.getFeelsLike();
        this.pressure = weather.getPressure();
        this.humidity = weather.getHumidity();
        this.wind_speed = weather.getWindSpeed();
        this.description = getDescription();
    }
}
