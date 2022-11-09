package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.payload.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
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


    public void addUser(List<String> user) {
        User newUser = new User();

        newUser.setUsername(user.get(0));
        newUser.setRole("ROLE_USER");
        newUser.setFirstName(user.get(1));
        newUser.setLastName(user.get(2));
        newUser.setAddress(user.get(3));
        newUser.setPassword(encoder.encode(user.get(4)));
        userRepository.save(newUser);
    }


    public void updateUser(User user) {
        User newUser = userRepository.findUserByUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setAddress(user.getAddress());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
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
        List<Device> deviceList = deviceRepository.findAll();
        User user = this.getUserByUsername(username);
        for (Device device : deviceList) {

            if (device.getUser() != null && device.getUser().getId() == user.getId()) {
                device.setUser(null);
                measurementRepository.deleteMeasurementByIDDevice(device.getId());
                deviceRepository.save(device);

            }
        }
        userRepository.delete(user);

    }


}
