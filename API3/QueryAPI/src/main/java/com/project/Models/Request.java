package com.project.Models;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_id", nullable = false)
    private Integer id;

    @Column(name = "req_date", nullable = false)
    private OffsetDateTime reqDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City cities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zip_num")
    private ZipCode zipCodes;

    public ZipCode getZipCodes() {
        return zipCodes;
    }

    public void setZipCodes(ZipCode zipCodes) {
        this.zipCodes = zipCodes;
    }

    public City getCities() {
        return cities;
    }

    public void setCities(City cities) {
        this.cities = cities;
    }

    public OffsetDateTime getReqDate() {
        return reqDate;
    }

    public void setReqDate(OffsetDateTime reqDate) {
        this.reqDate = reqDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}