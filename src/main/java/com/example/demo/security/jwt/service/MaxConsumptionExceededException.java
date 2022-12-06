package com.example.demo.security.jwt.service;

import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MaxConsumptionExceededException extends RuntimeException {

    private NotificationDTO notificationDTO;
    private Integer userId;


    public MaxConsumptionExceededException( Integer userId, NotificationDTO notificationDTO) {

        this.userId = userId;
        this.notificationDTO = notificationDTO;
    }


}
