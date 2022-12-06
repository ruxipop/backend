package com.example.demo.repository;

import com.example.demo.entities.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import javax.transaction.*;
import java.util.*;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    List<Notification> findAllByUserId(Integer userID);

    @Transactional
    void deleteAllByUserUsername(String username);
}
