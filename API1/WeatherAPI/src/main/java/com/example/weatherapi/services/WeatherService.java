package com.example.weatherapi.services;

import com.example.weatherapi.dao.CityRepo;
import com.example.weatherapi.dao.RequestRepo;
import com.example.weatherapi.dao.WeatherRepo;
import com.example.weatherapi.dto.WeatherDTO;
import com.example.weatherapi.models.City;
import com.example.weatherapi.models.Request;
import com.example.weatherapi.models.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeatherService {
    private final WeatherRepo weatherRepo;
    private final RequestRepo requestRepo;
    private final CityRepo cityRepo;

    /**
     * @param weatherRepo Autowiring Repositories
     */
    @Autowired
    public WeatherService(WeatherRepo weatherRepo, RequestRepo requestRepo, CityRepo cityRepo) {
        this.weatherRepo = weatherRepo;
        this.requestRepo = requestRepo;
        this.cityRepo = cityRepo;
    }

    /**
     * @param location Location for weather info
     * @return Returns request ID if weather data for this location is already available
     */
    public Integer checkData(String location){  //  Location could be zipCode or cityName
        int zip_city = 0;

        OffsetDateTime requestDate = OffsetDateTime.now();
        Request request;

        try{
            zip_city = Integer.parseInt(location);
            request = requestRepo.getRequestByZipCodes_Id(zip_city);

        }catch (NumberFormatException e){
            City city = cityRepo.getByCityName(location);
            zip_city = city.getId();
            request = requestRepo.getRequestByCitiesIdAndReqDate(zip_city);
        }

        //Request request = requestRepo.getByCitiesIdOrZipCodes_Id(zip_city, Optional.of(zip_city));

        log.info("-> request: {}", request);

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
        if(checkData(location) == -1){
            return null;
        }
        //  Get current weather. Convert raw data to dto
        List<Weather> weatherList = weatherRepo.getForecastByCity(checkData(location));

        return weatherList.stream()
                .map(w -> new WeatherDTO(w.getWeatherDate().toString(), w.getTemperature(), w.getFeelsLike(), w.getPressure(), w.getHumidity(), w.getWindSpeed(), w.getDescription()))
                .collect(Collectors.toList());
    }

}
