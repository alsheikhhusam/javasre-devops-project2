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

    /**
     * @param requestRepository Autowired Repository
     * @param cityRepository Autowired Repository
     * @param zipRepository Autowired Repository
     */

    @Autowired
    public RequestService(RequestRepository requestRepository, CityRepository cityRepository, ZipRepository zipRepository) {
        this.requestRepository = requestRepository;
        this.cityRepository = cityRepository;
        this.zipRepository = zipRepository;
    }

    /**
     * @param requestDTO Saves all location data requested from API into the database.
     * @return integer value for weather service
     */
    public int save(RequestDTO requestDTO){
        Request request = new Request();
        request.setReqDate(requestDTO.getReqDate());
        request.setZipCodes(requestDTO.getZipCodes());
        request.setCities(requestDTO.getCities());
        //requestRepository.save(request);
        return requestRepository.save(request).getId();
    }

    /**
     * @param cityName Takes in city name.
     * @return City Object based on the city name.
     */
    public City getCity(String cityName){
        return cityRepository.getCityByCityName(cityName);
    }
    /**
     * @param zipCode Takes in zip code.
     * @return Zipcode based on its ID.
     */
    public ZipCode getZipCode(int zipCode) {
        return zipRepository.getById(zipCode);
    }

    /**
     * @param reqId Takes in request ID
     * @return Request ID based on given ID.
     */
    public Request getRequest(int reqId){
        return requestRepository.getById(reqId);
    }

    /**
     * @param latNum Latitude value associated to Zipcode.
     * @param longNum Longitude value associated to Zipcode.
     * @return zipcode based on the given latitude and longitude values.
     */
    public ZipCode getZipCodeByLatNumAndLongNum(BigDecimal latNum, BigDecimal longNum){
        return zipRepository.getZipCodeByLatNumAndLongNum(latNum, longNum);
    }

    /**
     * @param latNum Latitude value associated to City.
     * @param longNum Longitude value associated to City.
     * @return city based on the given latitude and longitude values.
     */
    public City getCityByLatNumAndLongNum(BigDecimal latNum, BigDecimal longNum){
        return cityRepository.getCityByLatNumAndLongNum(latNum, longNum);
    }
}
