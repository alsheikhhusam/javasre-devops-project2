package com.project;

import com.project.Controller.WeatherController;
import com.project.DTO.RequestDTO;
import com.project.DTO.WeatherLoc;
import com.project.Models.City;
import com.project.Models.ZipCode;
import com.project.Service.RequestService;
import com.project.Service.WeatherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private MockRestServiceServer mockServer;

    @BeforeAll
    void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnOkay() throws Exception {
        String response = "";
        int id = 3;
        RequestDTO requestDTO = new RequestDTO();
        when(requestService.getZipCode(anyInt())).thenReturn(new ZipCode());
        when(requestService.getCity(anyString())).thenReturn(new City());
        when(restTemplate.getForObject(anyString(), String.class)).thenReturn(response);
        when(requestService.save(requestDTO)).thenReturn(id);

//        mockServer.expect(ExpectedCount.once())
//                .andExpect(method(HttpMethod.GET))
//                        .andRespond();

        requestService.save(any());
        mockMvc.perform(post("/update-db")
                        .contentType("text/plain;charset=UTF-8")
                .content("Redmond")
        ).andExpect(status().isOk());
    }
}
