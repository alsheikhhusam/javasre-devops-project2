package com.project.Service;

import com.project.DAO.RequestRepository;
import com.project.DAO.WeatherRepository;
import com.project.DTO.ModelDTO;
import com.project.Models.Request;
import com.project.Models.Weather;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes =  {WeatherService.class, WeatherRepository.class})
class WeatherServiceTest {

    @MockBean
    WeatherRepository weatherRepository;

    @MockBean
    RequestRepository requestRepository;

    @Autowired
    WeatherService weatherService;

    @Test
    void save() {
        ModelDTO testDTO = new ModelDTO(0,OffsetDateTime.now(),
                32.0,
                23.0,
                1000.0,
                0.0,
                5.0,
                "Test",
                new Request());
        Weather testWeather = new Weather();
        testWeather.setDate(testDTO.getDate());
        testWeather.setTemperature(testDTO.getTemperature());
        testWeather.setFeelsLike(testDTO.getFeelsLike());
        testWeather.setPressure(testDTO.getPressure());
        testWeather.setHumidity(testDTO.getHumidity());
        testWeather.setWindSpeed(testDTO.getWindSpeed());
        testWeather.setDescription(testDTO.getDescription());
        testWeather.setRequests(testDTO.getRequest());
        when(weatherRepository.save(testWeather)).thenReturn(new Weather());
        doNothing().when(weatherService).save(testDTO);
    }
}