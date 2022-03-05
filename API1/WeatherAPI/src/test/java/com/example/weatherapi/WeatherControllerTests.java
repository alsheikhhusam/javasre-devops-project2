package com.example.weatherapi;

import com.example.weatherapi.controllers.WeatherController;
import com.example.weatherapi.dto.TextDTO;
import com.example.weatherapi.dto.WeatherDTO;
import com.example.weatherapi.dto.smsDTO;
import com.example.weatherapi.services.WeatherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    WeatherService weatherService;

    @Value("8080")
    int port;

    @Value("${api.config.api2URL:http://localhost:3030/api-two/send-text}")
    String url2;

    @Value("${api.config.api3URL:http://localhost:8081/api-three/request/update-db}")
    String url3;

    TextDTO textDTO = new TextDTO("7192460901", "Davis");
    WeatherDTO weatherDTO = new WeatherDTO("2022-01-12", 80.0,70.0,60.0,50.0,
            40.0, "");
    ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.OK);

    @Test
    void shouldSendText() throws Exception {
        List<String> smsString = new java.util.ArrayList<>(Collections.singletonList(textDTO.getPhoneNum()));
        smsString.add(new smsDTO(textDTO.getLocation(), weatherDTO.toString()).toString());

        when(weatherService.getCurrentWeather(textDTO.getLocation())).thenReturn(weatherDTO);

        when(restTemplate.postForEntity(url3, textDTO.getLocation(), null))
                .thenReturn(responseEntity);
        when(restTemplate.postForEntity(url2, smsString, null))
                .thenReturn(ResponseEntity.accepted().build());

        mockMvc.perform(post("/weather/send-text")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"phoneNum\": \"7192460901\", \"location\": \"Davis\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrow500Error() throws Exception {
        List<String> smsString = new java.util.ArrayList<>(Collections.singletonList(textDTO.getPhoneNum()));
        smsString.add(new smsDTO(textDTO.getLocation(), weatherDTO.toString()).toString());

        when(weatherService.getCurrentWeather(textDTO.getLocation())).thenReturn(weatherDTO);

        when(restTemplate.postForEntity(url3, textDTO.getLocation(), null))
                .thenReturn(responseEntity);
        when(restTemplate.postForEntity(url2, smsString, null))
                .thenReturn(ResponseEntity.internalServerError().build());

        mockMvc.perform(post("/weather/send-text")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"phoneNum\": \"7192460901\", \"location\": \"Davis\" }"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldCallAPI3WhenWeatherNull() throws Exception {
        List<String> smsString = new java.util.ArrayList<>(Collections.singletonList(textDTO.getPhoneNum()));
        smsString.add(new smsDTO(textDTO.getLocation(), weatherDTO.toString()).toString());

        when(weatherService.getCurrentWeather(textDTO.getLocation())).thenReturn(null);
        when(weatherService.getCurrentWeather(textDTO.getLocation())).thenReturn(weatherDTO);
        when(restTemplate.postForEntity(url3, textDTO.getLocation(), null))
                .thenReturn(ResponseEntity.ok().build());
        when(restTemplate.postForEntity(url2, smsString, null))
                .thenReturn(ResponseEntity.accepted().build());

        mockMvc.perform(post("/weather/send-text")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"phoneNum\": \"7192460901\", \"location\": \"Davis\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallAPI3WhenWeatherNull_AndThrowErrorIfCannotReach() throws Exception {
        List<String> smsString = new java.util.ArrayList<>(Collections.singletonList(textDTO.getPhoneNum()));
        smsString.add(new smsDTO(textDTO.getLocation(), weatherDTO.toString()).toString());

        when(weatherService.getCurrentWeather(textDTO.getLocation())).thenReturn(null);

        when(restTemplate.postForEntity(url3, textDTO.getLocation(), null))
                .thenReturn(ResponseEntity.internalServerError().build());
        when(restTemplate.postForEntity(url2, smsString, null))
                .thenReturn(ResponseEntity.accepted().build());

        mockMvc.perform(post("/weather/send-text")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"phoneNum\": \"7192460901\", \"location\": \"Davis\" }"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldGetWeatherByCityName() throws Exception {
        String location = "Dallas";
        when(weatherService.getCurrentWeather(location)).thenReturn(weatherDTO);
        mockMvc.perform(get("/weather/get-current-weather")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content(location))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallAPI3IfNoWeatherInDatabase() throws Exception {
        String location = "Dallas";
        when(weatherService.getCurrentWeather(location)).thenReturn(null);
        when(restTemplate.postForEntity(url3, location, null)).thenReturn(ResponseEntity.accepted().build());
        mockMvc.perform(get("/weather/get-current-weather")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(location))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallAPI3IfNoWeatherInDatabase_AndThrowErrorIfStatus5xx() throws Exception {
        String location = "Dallas";
        when(weatherService.getCurrentWeather(location)).thenReturn(null);
        when(restTemplate.postForEntity(url3, location, null)).thenReturn(ResponseEntity.internalServerError().build());
        mockMvc.perform(get("/weather/get-current-weather")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(location))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldGetForecastByCityName() throws Exception {
        String location = "Dallas";
        List<WeatherDTO> weatherDTOList = Collections.singletonList(weatherDTO);
        when(weatherService.getForecast(location)).thenReturn(weatherDTOList);
        mockMvc.perform(get("/weather/get-forecast")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(location))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallAPI3IfNoForecastInDatabase() throws Exception {
        String location = "Dallas";
        when(weatherService.getForecast(location)).thenReturn(null);
        when(restTemplate.postForEntity(url3, location, null)).thenReturn(ResponseEntity.accepted().build());
        mockMvc.perform(get("/weather/get-forecast")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(location))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallAPI3IfNoForecastInDatabase_AndThrowErrorIfStatus5xx() throws Exception {
        String location = "Dallas";
        when(weatherService.getForecast(location)).thenReturn(null);
        when(restTemplate.postForEntity(url3, location, null)).thenReturn(ResponseEntity.internalServerError().build());
        mockMvc.perform(get("/weather/get-forecast")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(location))
                .andExpect(status().isInternalServerError());
    }
}
