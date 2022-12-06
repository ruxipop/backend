package com.example.demo.entities.dto;


import com.example.demo.entities.*;
import lombok.*;

import javax.persistence.criteria.*;
import java.sql.*;

@Builder
@Getter
@Setter
public class NotificationDTO {
    private Integer id;
    private Integer idUser;
    private String message;
    private String dates;

    public static NotificationDTO convertTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .idUser(notification.getUser().getId())
                .dates(notification.getDates())
                .build();
    }

}
