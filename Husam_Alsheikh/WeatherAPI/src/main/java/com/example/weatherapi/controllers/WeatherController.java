package com.example.weatherapi.controllers;

import com.example.weatherapi.dto.WeatherDTO;
import com.example.weatherapi.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("weather")
public class WeatherController {
    @Value("8080")
    int port;

    @Value("${api.config.api2URL:http://localhost:3000/api-two/text/send-text}")
    String url2;

    @Value("${api.config.api3URL:http://localhost:3000/api-three/update-db}")
    String url3;

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherService weatherService){
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
    }

    @GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, path = "get-current-weather")
    public ResponseEntity<?> getCurrentWeather(@RequestBody String location){
        //  Search DB for weather data on this location
        WeatherDTO weatherDTO = weatherService.getCurrentWeather(location);

        //  If weatherDTO is null, no weather data found on location. Call API3 //  FIX ME
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url3, location, null);
        if(responseEntity.getStatusCode().is5xxServerError()){
            log.error("-> Failed to connect to API 3");
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Hello World");
    }
}
