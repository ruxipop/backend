package com.example.demo.controller;

import com.example.demo.entities.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;


import java.sql.*;
import java.util.*;

import java.util.stream.*;

@RequestMapping("/measurement")
@CrossOrigin
@RestController
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    private ChartData converTo(Measurement measurement) {
        return ChartData.builder().hour(String.format("%02d", measurement.getTimestamp().getHours()))
                .consumation(measurement.getConsumation())
                .build();

    }

    @GetMapping("/consumation/{idDevice}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<ChartData> getMeasurements(@PathVariable Integer idDevice, @RequestParam("day") Integer day, @RequestParam("month") Integer month, @RequestParam("year") Integer year) {

        Timestamp dataCurrent = this.measurementService.setDateToTimestamp(day, month, year, true);
        Timestamp endDate = this.measurementService.setDateToTimestamp(day, month, year, false);
        List<Measurement> measurementList = measurementService.getDataConsumation(idDevice, dataCurrent, endDate);
        System.out.println("masuratori " + measurementList.stream().map(measurement -> converTo(measurement)).collect(Collectors.toList()));
        return measurementList.stream().map(measurement -> converTo(measurement)).collect(Collectors.toList());
    }


}
