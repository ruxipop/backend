package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public void deleteDevice(Integer id) {
        measurementRepository.deleteMeasurementByDevice(deviceRepository.getDeviceById(id));

        deviceRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return deviceRepository.findAllUser();
    }

    public Device getDeviceById(Integer id) {
        return deviceRepository.getDeviceById(id);
    }

    public void updateDevice(Device device) {
        deviceRepository.save(device);
    }

    public void addDevice(Device device) {
        deviceRepository.save(device);
//        return newDevice;
    }

    public User findOwner(Integer idDevice) {
        return deviceRepository.findOwner(idDevice);
    }


    public void update(Integer idOwner, Device device) {

        if (idOwner == 0) {
           device.setUser(null);
        } else {
            device.setUser(this.userRepository.findUserById(idOwner).get());

        }
        deviceRepository.save(device);
    }

    public List<User> getUsersWithoutDevices() {
        List<User> allUsers = userRepository.findAllByRole("ROLE_USER");
        List<User> usersWithDevices = this.getUsers();
        if (usersWithDevices.isEmpty()) {
            return allUsers;
        }
        return
                allUsers.stream()
                        .filter(e -> usersWithDevices.stream().map(User::getId).anyMatch(id -> id != e.getId()))
                        .collect(Collectors.toList());
    }

    public User addUserToDevice( Integer idUser) {

        User user = userRepository.findUserById(idUser).get();
        return  user;
    }
}
