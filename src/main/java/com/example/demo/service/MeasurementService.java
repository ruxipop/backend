package com.example.demo.service;


import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import com.example.demo.repository.*;
import com.example.demo.security.jwt.service.*;
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
@Slf4j
public class MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private NotificationRepository notificationRepository;


    public void setEnergyToDevice(MeasurementDTO measurementDTO) {
        Device device = deviceRepository.getDeviceById(measurementDTO.getDeviceID());
        List<Measurement> m = measurementRepository.findCalc();

        Measurement newMeasurement = Measurement.builder().device(device).consumation(measurementDTO.getConsumation()).timestamp(measurementDTO.getTimestamp()).build();
        measurementRepository.save(newMeasurement);
        Double lastValues = 0.0;
        if (m.size() >= 5) {

            for (int i = 0; i < 5; i++) {
                lastValues += m.get(i).getConsumation();

            }
            Timestamp lastMeasurement = m.get(4).getTimestamp();
            this.exceededConsumption(device, newMeasurement, lastMeasurement, lastValues);
        }


    }

    public void exceededConsumption(Device device, Measurement newMeasurement, Timestamp lastMeasurement, Double values) {
        if (lastMeasurement == null) {
            return;
        }
        double consumation = newMeasurement.getConsumation() + values;
        if ((consumation / 6) <= device.getMaxConsumation()) return;
        String message = "Device " + device.getAddress() + " exceeded the max consumation  with " + consumation / 6;
        String dates = "Last measurement was made at  " + lastMeasurement + " and the current one at " + newMeasurement.getTimestamp();
        Notification notification = Notification.builder().message(message).user(device.getUser()).dates(dates).build();
        notificationRepository.save(notification);

        NotificationDTO notificationDTO = NotificationDTO.builder().idUser(notification.getUser().getId()).dates(notification.getDates()).message(notification.getMessage()).build();
        throw new MaxConsumptionExceededException(notification.getUser().getId(), notificationDTO);
    }


    public List<Measurement> getDataConsumation(Integer idDevice, Timestamp chooseDate, Timestamp endDate) {
        return measurementRepository.findMeasurementByIdDevice(idDevice, chooseDate, endDate);

    }


    public Timestamp setDateToTimestamp(Integer day, Integer month, Integer year, Boolean isStartDate) {
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

}
