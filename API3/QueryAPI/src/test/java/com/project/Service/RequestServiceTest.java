package com.project.Service;

import com.project.DAO.CityRepository;
import com.project.DAO.RequestRepository;
import com.project.DAO.ZipRepository;
import com.project.DTO.RequestDTO;
import com.project.Models.City;
import com.project.Models.Request;
import com.project.Models.ZipCode;
import javafx.beans.binding.When;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes =  {RequestService.class, RequestRepository.class})
class RequestServiceTest {

    @MockBean
    private RequestRepository requestRepository;

    @MockBean
    private CityRepository cityRepository;

    @MockBean
    private ZipRepository zipRepository;

    @Autowired
    private RequestService requestService;

    @Test
    public void save() {
        RequestDTO requestDTO = new RequestDTO(900,
                OffsetDateTime.now(),
                new City(), new ZipCode());
        Request request = new Request();
        request.setReqDate(requestDTO.getReqDate());
        request.setZipCodes(requestDTO.getZipCodes());
        request.setCities(requestDTO.getCities());
        when(requestRepository.save(request)).thenReturn(request);
        when(requestService.save(requestDTO)).thenReturn(requestDTO.getId());
    }

    @Test
    void getCity() {
        String location = "Davis";
        when(cityRepository.getCityByCityName(location)).thenReturn(new City());
        when(requestService.getCity(location)).thenReturn(new City());
    }

    @Test
    void getZipCode() {
        int zipCode = 1080;
        when(zipRepository.getById(zipCode)).thenReturn(new ZipCode());
        when(requestService.getZipCode(zipCode)).thenReturn(new ZipCode());
    }

    @Test
    void getRequest() {
        int reqId = 86;
        when(requestRepository.getById(reqId)).thenReturn(new Request());
        when(requestService.getRequest(reqId)).thenReturn(new Request());
    }

    @Test
    void getZipCodeByLatNumAndLongNum() {
        BigDecimal latNum = BigDecimal.valueOf(18.180555);
        BigDecimal longNum = BigDecimal.valueOf(-66.749961);
        when(zipRepository.getZipCodeByLatNumAndLongNum(latNum, longNum)).thenReturn(new ZipCode());
        when(requestService.getZipCodeByLatNumAndLongNum(latNum, longNum)).thenReturn(new ZipCode());
    }

    @Test
    void getCityByLatNumAndLongNum() {
        BigDecimal latNum = BigDecimal.valueOf(38.544907);
        BigDecimal longNum = BigDecimal.valueOf(-121.740517);
        when(cityRepository.getCityByLatNumAndLongNum(latNum, longNum)).thenReturn(new City());
        when(requestService.getCityByLatNumAndLongNum(latNum, longNum)).thenReturn(new City());
    }
}