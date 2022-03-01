package com.project.DAO;

import com.project.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City getCityByCityName(String cityName);
    City getCityByLatNumAndLongNum(BigDecimal latNum, BigDecimal longNum);
}
