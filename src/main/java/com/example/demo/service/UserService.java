package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;


import java.util.*;

@Service
public class UserService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeasurementRepository measurementRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getAllByRole(String role) {
        return userRepository.findAllByRole(role);

    }


    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


    public void addUser(UserDTO userDTO) {
        userRepository.save(userDTO.convertTO());

    }


    public void updateUser(UserDTO userDTO) {
        userRepository.save(userDTO.convertTO());
    }

    public Device getDeviceOfGivenOwner(String username) {
        List<Device> deviceList = deviceRepository.findAll();
        User user = this.getUserByUsername(username);
        for (Device device : deviceList) {

            if (device.getUser() != null && device.getUser().getId() == user.getId()) {
                return device;
            }
        }
        return null;
    }


    public void deleteUserByUsername(String username) {
        Device device = deviceRepository.findDeviceByUserUsername(username);
        if (device != null) {
            device.setUser(null);
            deviceRepository.save(device);
            measurementRepository.deleteMeasurementByDevice(device);
        }
        userRepository.delete(this.getUserByUsername(username));

    }

}
