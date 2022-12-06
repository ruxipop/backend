package com.example.demo.repository;

import com.example.demo.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.*;

import javax.persistence.criteria.*;
import java.util.*;

public interface DeviceRepository  extends CrudRepository<Device, Integer> {
    @Query("SELECT device FROM device device")
    List<Device> findAll();

    @Query("SELECT device.user FROM device device")
    List<User> findAllUser();

    Device getDeviceById(Integer id);

    @Query("SELECT device.user FROM device device WHERE  device.id=:idDevice")
    User findOwner(@Param("idDevice") Integer idDevice);


    Device findDeviceByUserUsername(String username);
}
