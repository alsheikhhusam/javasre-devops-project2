package com.example.weatherapi.dao;


import com.example.weatherapi.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface WeatherRepo extends JpaRepository<Weather, Integer> {
    @Query(value = "select * from weather w where (cast(NOW() as date) = cast(w.weather_date as date)) and w.req_id = :req_id order by w.weather_date", nativeQuery = true)
    Weather getCurrentByCity(@Param("req_id") Integer req_id);

    @Query(value = "select * from weather w where w.req_id = :req_id order by w.weather_date", nativeQuery = true)
    List<Weather> getForecastByCity(@Param("req_id") Integer req_id);
}
