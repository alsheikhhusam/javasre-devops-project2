package com.project.DAO;

import com.project.Models.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ZipRepository extends JpaRepository<ZipCode, Integer> {
    ZipCode getZipCodeByLatNumAndLongNum(BigDecimal latNum, BigDecimal longNum);
}
