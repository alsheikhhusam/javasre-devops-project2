package com.project.Models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone_num", nullable = false)
    private Long phoneNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City cities;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}