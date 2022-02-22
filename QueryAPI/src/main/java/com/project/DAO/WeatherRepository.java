package com.project.DAO;

import com.project.Models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class WeatherRepository extends JpaRepository<Weather, Integer>{

}
