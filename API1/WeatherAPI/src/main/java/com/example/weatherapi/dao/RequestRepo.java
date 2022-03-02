package com.example.weatherapi.dao;

import com.example.weatherapi.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public interface RequestRepo extends JpaRepository<Request, Integer> {
    @Query(value = "select * from requests r where (cast(NOW() as date) = cast(r.req_date as date)) and r.city_id = :city_id", nativeQuery = true)
    Request getRequestByCitiesIdAndReqDate(@Param("city_id") Integer location);

    @Query(value = "select * from requests r where (cast(NOW() as date) = cast(r.req_date as date)) and r.zip_num = :zip_num", nativeQuery = true)
    Request getRequestByZipCodes_Id(@Param("zip_num") Integer location);
}
