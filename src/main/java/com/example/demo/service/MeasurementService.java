package com.example.demo.service;


import com.example.demo.entities.*;
import com.example.demo.repository.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.transaction.*;
import java.sql.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;

    @Transactional
    public void deleteMeasurement(Integer idDevice){
       measurementRepository.deleteMeasurementByIDDevice(idDevice);
    }

    public void setEnergyToDevice(Device device){
        Measurement measurement=new Measurement(new Timestamp(System.currentTimeMillis()),(double)(Math.random()*device.getMaxConsumation()));
        measurement.setDevice(device);
        measurementRepository.save(measurement);
    }

//    @Transactional
    public List<Measurement> getDataConsumation(Integer idDevice, Timestamp chooseDate,Timestamp endDate){
        Timestamp startDate=chooseDate;

        return measurementRepository.findMeasurementByIdDevice(idDevice,startDate,endDate);

    }
}
