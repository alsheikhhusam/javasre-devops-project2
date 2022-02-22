package com.example.weatherapi.controllers;

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

    @Value("${api.config.api3Curr:http://localhost:3000/api-three/current}")
    String api3_current;

    @Value("${api.config.api3SevenDay:http://localhost:3000/api-three/7-day-forecast}")
    String api3_seven_day;

    @Value("${api.config.apiPrevFive:http://localhost:3000/api-three/prev-5}")
    String api3_prev_5;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, path = "get-current-weather")
    public ResponseEntity<?> getCurrentWeather(@RequestBody String location){
        //  Check if string is all digits. Then convert zip-code to int

        //  Search DB for weather data on this location


        //  If data not found. Then call api 3  //  FIX ME
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(api3_current, location, null);
        if(responseEntity.getStatusCode().is5xxServerError()){
            log.error("-> Failed to connect to API 3");
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Hello World");
    }
}
