package com.project.Service;

import com.project.DAO.WeatherRepository;
import com.project.DTO.ModelDTO;
import com.project.Models.Weather;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    public void save(ModelDTO weather) {
        Weather newWeather = new Weather();
        newWeather.setDate(weather.getDate());
        newWeather.setTemperature(weather.getTemperature());
        newWeather.setFeelsLike(weather.getFeelsLike());
        newWeather.setPressure(weather.getPressure());
        newWeather.setHumidity(weather.getHumidity());
        newWeather.setWindSpeed(weather.getWindSpeed());
        newWeather.setDescription(weather.getDescription());
        newWeather.setRequests(weather.getRequest());
        weatherRepository.save(newWeather);
    }


}
