package com.example.weatherapi;

import com.example.weatherapi.dao.CityRepo;
import com.example.weatherapi.dao.RequestRepo;
import com.example.weatherapi.dao.WeatherRepo;
import com.example.weatherapi.dto.WeatherDTO;
import com.example.weatherapi.models.City;
import com.example.weatherapi.models.Request;
import com.example.weatherapi.models.Weather;
import com.example.weatherapi.models.ZipCode;
import com.example.weatherapi.services.WeatherService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherServiceTests {

    @Value("8080")
    int port;

    @Value("${api.config.api2URL:http://localhost:3030/api-two/send-text}")
    String url2;

    @Value("${api.config.api3URL:http://localhost:8081/api-three/request/update-db}")
    String url3;

    @MockBean
    private WeatherRepo weatherRepo;

    @MockBean
    private RequestRepo requestRepo;

    @MockBean
    private CityRepo cityRepo;

    @Autowired
    WeatherService weatherService;

    String cityLocation = "Dallas";
    String zipLocation = "14610";

    City city = new City(1, cityLocation, BigDecimal.valueOf(1.0), BigDecimal.valueOf(1.0), "Texas");
    ZipCode zipCode = new ZipCode(2, BigDecimal.valueOf(2.0), BigDecimal.valueOf(2.0));
    Request request = new Request(1, OffsetDateTime.now(), city, zipCode);
    Weather weather = new Weather(1,OffsetDateTime.now(), 80.0,70.0,60.0,50.0,
            40.0, "", request);

    @Test
    void shouldReturnRequestIdWhenSearchByZip() {
        when(requestRepo.getRequestByZipCodes_Id(anyInt())).thenReturn(request);
        int result = weatherService.checkData(zipLocation);
        assertEquals(1, result);
    }

    @Test
    void shouldReturnRequestIdWhenSearchByCity() {
        when(cityRepo.getByCityName(cityLocation)).thenReturn(city);
        when(requestRepo.getRequestByCitiesIdAndReqDate(city.getId())).thenReturn(request);
        int result = weatherService.checkData(cityLocation);
        assertEquals(result, 1);
    }

    @Test
    void shouldReturnNegativeRequestIdWhenRequestIsNull() {
        when(requestRepo.getRequestByZipCodes_Id(anyInt())).thenReturn(null);
        int result = weatherService.checkData(zipLocation);
        assertEquals(-1, result);
    }

    @Test
    void shouldGetCurrentWeather() {
        int id = 1;

        when(cityRepo.getByCityName(cityLocation)).thenReturn(city);
        when(requestRepo.getRequestByCitiesIdAndReqDate(city.getId())).thenReturn(request);
        when(weatherRepo.getCurrentByCity(id)).thenReturn(weather);

        WeatherDTO weatherDTO = weatherService.getCurrentWeather(cityLocation);
        assertEquals(80.0, weatherDTO.getTemp());
    }

    @Test
    void shouldReturnNullIfInvalidLocation() {
        when(cityRepo.getByCityName(cityLocation)).thenReturn(city);
        when(requestRepo.getRequestByCitiesIdAndReqDate(city.getId())).thenReturn(null);
        WeatherDTO weatherDTO = weatherService.getCurrentWeather(cityLocation);
        assertNull(weatherDTO);
    }

    @Test
    void shouldGetForecast() {
        List<Weather> listWeather = Collections.singletonList(weather);

        when(cityRepo.getByCityName(cityLocation)).thenReturn(city);
        when(requestRepo.getRequestByCitiesIdAndReqDate(city.getId())).thenReturn(request);
        when(weatherRepo.getForecastByCity(anyInt())).thenReturn(listWeather);

        List<WeatherDTO> weatherDTOList = weatherService.getForecast(cityLocation);
        assertEquals(weatherDTOList.size(), 1);
    }

    @Test
    void shouldReturnNullForecastIfInvalidLocation() {
        when(cityRepo.getByCityName(cityLocation)).thenReturn(city);
        when(requestRepo.getRequestByCitiesIdAndReqDate(city.getId())).thenReturn(null);

        List<WeatherDTO> weatherDTOList = weatherService.getForecast(cityLocation);
        assertNull(weatherDTOList);
    }
}
