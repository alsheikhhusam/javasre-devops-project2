package com.project.DTO;

import com.project.Models.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO implements Serializable {

    private Integer id;

    private OffsetDateTime date;

    private Double temperature;

    private Double feelsLike;

    private Double pressure;

    private Double humidity;

    private Double windSpeed;

    private String description;

    private Request request;

}
