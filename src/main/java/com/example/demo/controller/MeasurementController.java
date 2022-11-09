package com.example.demo.controller;

import com.example.demo.entities.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.*;
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

    private Timestamp setDateToTimestamp(Integer day, Integer month, Integer year, Boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if (!isStartDate) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }
        Date date = calendar.getTime();
        return new Timestamp(date.getTime());

    }

    @GetMapping("/consumation/{idDevice}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<ChartData> getMeasurements(@PathVariable Integer idDevice, @RequestParam("day") Integer day, @RequestParam("month") Integer month, @RequestParam("year") Integer year) {

        Timestamp dataCurrent = this.setDateToTimestamp(day, month, year, true);
        Timestamp endDate = this.setDateToTimestamp(day, month, year, false);
        List<Measurement> measurementList = measurementService.getDataConsumation(idDevice, dataCurrent, endDate);
        return measurementList.stream().map(measurement -> converTo(measurement)).collect(Collectors.toList());
    }


}
