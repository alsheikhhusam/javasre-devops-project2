package com.project.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.DTO.ModelDTO;
import com.project.DTO.RequestDTO;
import com.project.DTO.WeatherLoc;
import com.project.Models.City;
import com.project.Models.Request;
import com.project.Models.ZipCode;
import com.project.Service.RequestService;
import com.project.Service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RestController
@RequestMapping("request")
@Slf4j
public class WeatherController {

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;
    private final RequestService requestService;

    /**
     * @param restTemplate Autowired restTemplate
     * @param weatherService Autowired weatherService
     * @param requestService Autowird requestService
     */
    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherService weatherService, RequestService requestService) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
        this.requestService = requestService;
    }

    /**
     *
     * @param location City name or Zipcode
     * @return 202 Accepted
     * @throws Exception
     */
    @PostMapping(value = "/update-db")
    public ResponseEntity acknowledgeReceipt(@RequestBody String location) throws Exception {
        int zipCode = 0;
        String cityName = "";
        City cityObject = new City();
        ZipCode zipObject = new ZipCode();

        try{
            zipCode = Integer.parseInt(location);
            zipObject = requestService.getZipCode(zipCode);
        } catch(NumberFormatException e){
            zipCode = -1;
            zipObject.setId(0);
            zipObject.setLatNum(BigDecimal.valueOf(0));
            zipObject.setLongNum(BigDecimal.valueOf(0));

            cityName = location;
            cityObject = requestService.getCity(location);
            log.info(cityName);
        } catch(Exception ex) {
            log.debug("Location is invalid");
        }

        // Query DB for Lat/Long of ZipCode or CityName
        String latitude;
        String longitude;

        if(zipCode == -1){
            City temp = requestService.getCity(cityName);
            longitude = temp.getLongNum().toString();
            latitude = temp.getLatNum().toString();
            log.info("Longitude for "+ cityName + ": " + longitude);
            log.info("Latitude for "+ cityName + ": " + latitude);
        }
        else{
            ZipCode temp = requestService.getZipCode(zipCode);
            latitude = temp.getLatNum().toString();
            longitude = temp.getLongNum().toString();
            log.info("Longitude for "+ zipCode + ": " + longitude);
            log.info("Latitude for "+ zipCode + ": " + latitude);
        }

        // Grab Data from OpenWeather API
        String key = "f46a1727320302ec8f10c83db557ecf8";

        String theweather = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/onecall?lat="
                + latitude
                + "&lon="
                + longitude
                + "&exclude=current,minutely,hourly,alerts&units=imperial&appid="
                + key, String.class);
        log.info("JSON Value pulled from URL: " + theweather);

        Gson gson = new GsonBuilder().create();
        WeatherLoc weatherSaved = gson.fromJson(theweather, WeatherLoc.class);

        Date currentDate = new Date(System.currentTimeMillis());
        OffsetDateTime offsetCurrentDate = currentDate.toInstant().atOffset(ZoneOffset.UTC);

         RequestDTO requestDTO = new RequestDTO();
         requestDTO.setReqDate(offsetCurrentDate);
         cityObject = requestService.getCityByLatNumAndLongNum(new BigDecimal(latitude),new BigDecimal(longitude));
         zipObject = requestService.getZipCodeByLatNumAndLongNum(new BigDecimal(latitude), new BigDecimal(longitude));
         requestDTO.setCities(cityObject);
         requestDTO.setZipCodes(zipObject);

         log.info("Request information has been saved to the database.");

         int requestId = requestService.save(requestDTO);

         Request update = requestService.getRequest(requestId);


         ModelDTO weather = new ModelDTO();
             weather.setDate(offsetCurrentDate);
             weather.setTemperature(weatherSaved.daily[0].temp.max);
             weather.setFeelsLike(weatherSaved.daily[0].feels_like.day);
             weather.setPressure(weatherSaved.daily[0].pressure);
             weather.setHumidity(weatherSaved.daily[0].humidity);
             weather.setWindSpeed(weatherSaved.daily[0].wind_speed);
             weather.setDescription(weatherSaved.daily[0].weather[0].description);
             weather.setRequest(update);
             weatherService.save(weather);

             log.info("Current weather conditions have been added to the database.");


        for(int i = 1; i < weatherSaved.daily.length - 1; i++){
            currentDate = new Date(weatherSaved.daily[i].dt * 1000L);
            offsetCurrentDate = currentDate.toInstant().atOffset(ZoneOffset.UTC);
            weather.setDate(offsetCurrentDate);
            weather.setTemperature(weatherSaved.daily[i].temp.max);
            weather.setFeelsLike(weatherSaved.daily[i].feels_like.day);
            weather.setPressure(weatherSaved.daily[i].pressure);
            weather.setHumidity(weatherSaved.daily[i].humidity);
            weather.setWindSpeed(weatherSaved.daily[i].wind_speed);
            weather.setDescription(weatherSaved.daily[i].weather[0].description);
            weather.setRequest(update);
            weatherService.save(weather);

        }
        log.info("7 Day Forecast has been added to the database.");
        return ResponseEntity.accepted().build();

    }
}
