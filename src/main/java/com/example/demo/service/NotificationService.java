package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<NotificationDTO> getNotification(Integer userID){
        List<Notification> notificationList=notificationRepository.findAllByUserId(userID);
        return notificationList.stream().map(NotificationDTO::convertTO).collect(Collectors.toList());
    }


    public void deleteNotification(Integer idNotification){
        notificationRepository.delete(notificationRepository.findById(idNotification).get());
    }

    public void deleteNotificationByUsername(String username){
        notificationRepository.deleteAllByUserUsername(username);
    }
}
