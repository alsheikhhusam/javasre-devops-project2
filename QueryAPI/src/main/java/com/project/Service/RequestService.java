package com.project.Service;

import com.project.DAO.CityRepository;
import com.project.DAO.RequestRepository;
import com.project.DAO.ZipRepository;
import com.project.DTO.RequestDTO;
import com.project.Models.City;
import com.project.Models.Request;
import com.project.Models.ZipCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RequestService {

    RequestRepository requestRepository;
    CityRepository cityRepository;
    ZipRepository zipRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, CityRepository cityRepository, ZipRepository zipRepository) {
        this.requestRepository = requestRepository;
        this.cityRepository = cityRepository;
        this.zipRepository = zipRepository;
    }

    public int save(RequestDTO requestDTO){
        Request request = new Request();
        request.setReqDate(requestDTO.getReqDate());
        request.setZipCodes(requestDTO.getZipCodes());
        request.setCities(requestDTO.getCities());
        //requestRepository.save(request);
        return requestRepository.save(request).getId();
    }

    public City getCity(String cityName){
        return cityRepository.getCityByCityName(cityName);
    }

    public ZipCode getZipCode(int zipCode) {
        return zipRepository.getById(zipCode);
    }

    public Request getRequest(int reqId){
        return requestRepository.getById(reqId);
    }

    public ZipCode getZipCodeByLatNumAndLongNum(BigDecimal latNum, BigDecimal longNum){
        return zipRepository.getZipCodeByLatNumAndLongNum(latNum, longNum);
    }

    public City getCityByLatNumAndLongNum(BigDecimal latNum, BigDecimal longNum){
        return cityRepository.getCityByLatNumAndLongNum(latNum, longNum);
    }
}
