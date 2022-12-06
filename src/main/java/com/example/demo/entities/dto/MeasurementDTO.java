package com.example.demo.entities.dto;

import com.example.demo.entities.*;
import com.example.demo.service.*;
import lombok.*;

import javax.persistence.criteria.*;
import java.sql.*;
@Getter
@Setter
@Builder
public class MeasurementDTO {

    private Integer deviceID;
    private  Double consumation;
    private Timestamp timestamp;

    public MeasurementDTO(Integer deviceID, Double consumation, Timestamp timestamp) {
        this.deviceID = deviceID;
        this.consumation = consumation;
        this.timestamp = timestamp;
    }


}
