//package com.project;
//
//import com.project.Controller.WeatherController;
//import com.project.DTO.RequestDTO;
//import com.project.DTO.WeatherLoc;
//import com.project.Models.City;
//import com.project.Models.ZipCode;
//import com.project.Service.RequestService;
//import com.project.Service.WeatherService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.client.ExpectedCount;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
//
//@WebMvcTest(WeatherController.class)
//public class WeatherControllerTests {
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    private RequestService requestService;
//
//    @MockBean
//    private WeatherService weatherService;
//
//    @MockBean
//    private RestTemplate restTemplate;
//
//}
