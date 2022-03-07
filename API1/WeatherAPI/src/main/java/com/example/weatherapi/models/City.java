package com.example.weatherapi.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Integer id;

    @Column(name = "city_name", nullable = false, length = 40)
    private String cityName;

    @Column(name = "lat_num", nullable = false, precision = 8, scale = 6)
    private BigDecimal latNum;

    @Column(name = "long_num", nullable = false, precision = 9, scale = 6)
    private BigDecimal longNum;

    @Column(name = "state_name", length = 50)
    private String stateName;
}