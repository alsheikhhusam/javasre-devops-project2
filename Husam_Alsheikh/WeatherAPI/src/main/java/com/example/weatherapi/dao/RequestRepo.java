package com.example.weatherapi.dao;

import com.example.weatherapi.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RequestRepo extends JpaRepository<Request, Integer> {
    Request getByCities_CityNameOrZipCodes_Id(String location);
}
