package com.example.weatherapi.controllers;

import com.example.weatherapi.dto.TextDTO;
import com.example.weatherapi.dto.WeatherDTO;
import com.example.weatherapi.dto.smsDTO;
import com.example.weatherapi.models.Weather;
import com.example.weatherapi.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("weather")
public class WeatherController {
    @Value("8080")
    int port;

    @Value("${api.config.api2URL:http://localhost:3030/api-two/send-text}")
    String url2;

    @Value("${api.config.api3URL:http://localhost:8081/api-three/request/update-db}")
    String url3;

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;

    /**
     * @param restTemplate Autowire RestTemplate
     * @param weatherService Autowire WeatherService
     */
    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherService weatherService){
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
    }

    /**
     * @param textDTO SMS info
     * @return Returns 200 Status
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "send-text")
    public ResponseEntity<?> sendText(@RequestBody TextDTO textDTO){
        List<String> smsString = new java.util.ArrayList<>(Collections.singletonList(textDTO.getPhoneNum()));

        WeatherDTO weatherDTO = weatherService.getCurrentWeather(textDTO.getLocation());
<<<<<<< HEAD

        //  If weatherDTO is null, no weather data found on location. Call API3 (Query API)
        if(weatherDTO == null) {
            log.info("-> No data on {}", textDTO.getLocation());
            log.info("-> Requesting Weather Update for {}", textDTO.getLocation());

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url3, textDTO.getLocation(), null);
            if(responseEntity.getStatusCode().is5xxServerError()){
                log.error("-> Failed to connect to API 3");
                return ResponseEntity.internalServerError().build();
            }

            weatherDTO = weatherService.getCurrentWeather(textDTO.getLocation());
        }
        smsString.add(weatherService.getCurrentWeather(textDTO.getLocation()).toString());
=======
>>>>>>> 583cad32888173864c01b4139c335ce55ff7612f

        //  If weatherDTO is null, no weather data found on location. Call API3 (Query API)
        if(weatherDTO == null) {
            log.info("-> No data on {}" , textDTO.getLocation());
            log.info("-> Requesting Weather Update for {}" , textDTO.getLocation());

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url3, textDTO.getLocation(), null);
            if(responseEntity.getStatusCode().is5xxServerError()){
                log.error("-> Failed to connect to API 3");
                return ResponseEntity.internalServerError().build();
            }

            weatherDTO = weatherService.getCurrentWeather(textDTO.getLocation());
        }

        smsString.add(new smsDTO(textDTO.getLocation(), weatherDTO.toString()).toString());

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url2, smsString, null);
        if(responseEntity.getStatusCode().is5xxServerError()){
            log.error("-> Failed to connect to API 2");
            return ResponseEntity.internalServerError().build();
        }

        log.info("-> Text message sent");

        return ResponseEntity.ok().build();
    }

    /**
     * @param location Location for weather info
     * @return Returns Weather Data for current date
     */
    @GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, path = "get-current-weather")
    public ResponseEntity<?> getCurrentWeather(@RequestBody String location){
        //  Search DB for weather data on this location
        WeatherDTO weatherDTO = weatherService.getCurrentWeather(location);

        //  If weatherDTO is null, no weather data found on location. Call API3 (Query API)
        if(weatherDTO == null) {
            log.info("-> No data on {}" , location);
            log.info("-> Requesting Weather Update for {}" , location);

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url3, location, null);
            if(responseEntity.getStatusCode().is5xxServerError()){
                log.error("-> Failed to connect to API 3");
                return ResponseEntity.internalServerError().build();
            }

            weatherDTO = weatherService.getCurrentWeather(location);
        }

        return ResponseEntity.ok(weatherDTO);
    }

    /**
     * @param location Location for weather info
     * @return Weather data for 7-day forecast
     */
    @GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, path = "get-forecast")
    public ResponseEntity<?> getForecast(@RequestBody String location){
        //  Search DB for weather data on this location

        List<WeatherDTO> weatherDTOList = weatherService.getForecast(location);

        if(weatherDTOList == null){
            log.info("-> No data on {}" , location);
            log.info("-> Requesting Weather Update for {}" , location);
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url3, location, null);
            if(responseEntity.getStatusCode().is5xxServerError()){
                log.error("-> Failed to connect to API 3");
                return ResponseEntity.internalServerError().build();
        }
            weatherDTOList = weatherService.getForecast(location);
        }

        return  ResponseEntity.ok(weatherDTOList);
    }
}
