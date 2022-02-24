package com.example.weatherapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
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
}