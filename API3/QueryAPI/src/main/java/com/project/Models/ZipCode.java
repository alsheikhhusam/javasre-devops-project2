package com.project.Models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zip_codes")
public class ZipCode {
    @Id
    @Column(name = "zip_num", nullable = false)
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}