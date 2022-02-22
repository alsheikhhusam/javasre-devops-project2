package com.example.weatherapi.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Integer id;

    @Column(name = "city_name", length = 40)
    private String cityName;

    @Column(name = "lat_num", nullable = false, precision = 8, scale = 6)
    private BigDecimal latNum;

    @Column(name = "long_num", nullable = false, precision = 9, scale = 6)
    private BigDecimal longNum;

    public BigDecimal getLongNum() {
        return longNum;
    }

    public void setLongNum(BigDecimal longNum) {
        this.longNum = longNum;
    }

    public BigDecimal getLatNum() {
        return latNum;
    }

    public void setLatNum(BigDecimal latNum) {
        this.latNum = latNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}