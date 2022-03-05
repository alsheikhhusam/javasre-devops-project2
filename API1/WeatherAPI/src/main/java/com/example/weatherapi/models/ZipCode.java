package com.example.weatherapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zip_codes")
public class ZipCode {
    @Id
    @Column(name = "zip_num", nullable = false)
    private Integer id;

    @Column(name = "lat_num", nullable = false, precision = 8, scale = 6)
    private BigDecimal latNum;

    @Column(name = "long_num", nullable = false, precision = 9, scale = 6)
    private BigDecimal longNum;
}