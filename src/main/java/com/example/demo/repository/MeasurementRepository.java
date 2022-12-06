package com.example.demo.repository;

import com.example.demo.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.*;
import java.util.*;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {

    @Query(" FROM measurements me WHERE  me.device.id=:idDevice and me.timestamp BETWEEN :startDate AND :endDate ORDER BY  me.timestamp")
    List<Measurement> findMeasurementByIdDevice(@Param("idDevice") Integer idDevice,
                                                @Param("startDate") Timestamp startDate,
                                                @Param("endDate") Timestamp endDate);



    void deleteMeasurementByDevice(Device device);


    Measurement findTopByDeviceOrderByTimestampDesc(Device device);

    @Query(" FROM measurements ORDER BY id DESC")
    List<Measurement> findCalc();



}
