package com.project.DTO;

import com.project.Models.City;
import com.project.Models.ZipCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO implements Serializable {
    private Integer id;
    private OffsetDateTime reqDate;
    private City cities;
    private ZipCode zipCodes;
}
