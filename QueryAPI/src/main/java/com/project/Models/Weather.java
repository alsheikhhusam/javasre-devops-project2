package com.project.Models;

import com.project.NewEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Getter @Setter
@Table(name = "weather")

public class Weather {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "weather_id", nullable = false)
        private Integer id;

        @Column(name = "date", nullable = false)
        private OffsetDateTime date;

        @Column(name = "temperature", nullable = false)
        private Double temperature;

        @Column(name = "feels_like", nullable = false)
        private Double feelsLike;

        @Column(name = "pressure", nullable = false)
        private Double pressure;

        @Column(name = "humidity", nullable = false)
        private Double humidity;

        @Column(name = "wind_speed", nullable = false)
        private Double windSpeed;

        @Column(name = "description", nullable = false, length = 20)
        private String description;

        @ManyToOne
        @JoinColumn(name = "new_entity_ID")
        private NewEntity newEntity;

}

