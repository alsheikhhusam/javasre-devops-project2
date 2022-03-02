package com.example.weatherapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_id", nullable = false)
    private Integer id;

    @Column(name = "req_date", nullable = false)
    private OffsetDateTime reqDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City cities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zip_num")
    private ZipCode zipCodes;
}