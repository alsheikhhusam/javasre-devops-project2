package com.example.weatherapi.services;

import com.example.weatherapi.dao.RequestRepo;
import com.example.weatherapi.dao.WeatherRepo;
import com.example.weatherapi.dto.WeatherDTO;
import com.example.weatherapi.models.Request;
import com.example.weatherapi.models.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WeatherRepo weatherRepo;
    private final RequestRepo requestRepo;

    /**
     * @param weatherRepo Autowire Weather Repository and Request Repository
     */
    @Autowired
    public WeatherService(WeatherRepo weatherRepo, RequestRepo requestRepo) {
        this.weatherRepo = weatherRepo;
        this.requestRepo = requestRepo;
    }

    /**
     * @param location Location for weather info
     * @return Returns request ID if weather data for this location is already available
     */
    public Integer checkData(String location){
        Request request = requestRepo.getByCities_CityNameOrZipCodes_Id(location);

        if(request == null){
            return -1;
        }

        return request.getId();
    }

    /**
     * @param location Location for weather info
     * @return Weather data for current date
     */
    public WeatherDTO getCurrentWeather(String location){
        if(checkData(location) == -1){
            return null;
        }

        return new WeatherDTO(weatherRepo.getCurrentByCity(checkData(location)));
    }

    /**
     * @param location Location for weather info
     * @return Weather data for 7-day forecast
     */
    public List<WeatherDTO> getForecast(String location){
        //  If loc is zip-code. Convert to city_id/city_name

        //  Get current weather. Convert raw data to dto
        List<Weather> weatherList = weatherRepo.getForecastByCity(checkData(location));

        return weatherList.stream()
                .map(w -> new WeatherDTO(w.getDate().toString(), w.getTemperature(), w.getFeelsLike(), w.getPressure(), w.getHumidity(), w.getWindSpeed(), w.getDescription()))
                .collect(Collectors.toList());
    }

    /**
     * @param location Location for weather info
     * @return Weather data for prev 5-day
     */
    public List<WeatherDTO> getPrev(String location){
        //  If loc is zip-code. Convert to city_id/city_name

        //  Get current weather. Convert raw data to dto
        List<Weather> weatherList = weatherRepo.getPrevByCity(checkData(location));

        return weatherList.stream()
                .map(w -> new WeatherDTO(w.getDate().toString(), w.getTemperature(), w.getFeelsLike(), w.getPressure(), w.getHumidity(), w.getWindSpeed(), w.getDescription()))
                .collect(Collectors.toList());
    }
}
