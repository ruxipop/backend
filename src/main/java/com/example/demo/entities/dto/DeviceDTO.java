package com.example.demo.entities.dto;

import com.example.demo.entities.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;
@Builder
@Getter
@Setter
public class DeviceDTO {
    private Integer id;

    private String description;

    private String address;


    private  Double maxConsumation;

    private Collection<Measurement> measurements;

    private User user;
    public Device convertTO() {
        return Device.builder().id(id).description(description).address(address)
                .maxConsumation(maxConsumation).measurements(measurements).user(user)
                .build();
    }
    public static DeviceDTO convertTODTO(Device device) {
        return DeviceDTO.builder()
                .description(device.getDescription())
                .id(device.getId())
                .address(device.getAddress())
                .maxConsumation(device.getMaxConsumation())
                .measurements(device.getMeasurements())
                .user(device.getUser())
                .build();
    }
}
