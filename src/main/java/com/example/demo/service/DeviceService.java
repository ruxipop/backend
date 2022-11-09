package com.example.demo.service;

import com.example.demo.entities.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
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

    public Device addDevice(List<String> device) {
        Device newDevice = new Device();
        newDevice.setDescription(device.get(0));
        newDevice.setAddress(device.get(1));
        newDevice.setMaxConsumation(Double.parseDouble(device.get(2)));
        deviceRepository.save(newDevice);
        return newDevice;
    }

    public User findOwner(Integer idDevice) {
        return deviceRepository.findOwner(idDevice);
    }

//    public void update(List<String> newDevice, Integer user,Integer idDevice){
////        Device oldDevice = deviceRepository.getDeviceById(idDevice);
////        oldDevice.setDescription(newDevice.get(0));
////        oldDevice.setAddress(newDevice.get(1));
////        oldDevice.setMaxConsumation(Double.parseDouble(newDevice.get(2)));
////        oldDevice.setUser(user);
////        deviceRepository.save(oldDevice);
//
//    }

    public void update(Integer idOwner, Device device) {
        Device oldDevice = deviceRepository.getDeviceById(device.getId());


        oldDevice.setDescription(device.getDescription());
        oldDevice.setAddress(device.getAddress());
        oldDevice.setMaxConsumation(device.getMaxConsumation());
        oldDevice.setMeasurements(device.getMeasurements());
        if (idOwner == 0) {
            oldDevice.setUser(null);
        } else {
            oldDevice.setUser(this.userRepository.findUserById(idOwner).get());
        }
        deviceRepository.save(oldDevice);
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

    public Device addUserToDevice(Integer idDevice, Integer idUser) {
        Device device = this.getDeviceById(idDevice);
        User user = userRepository.findUserById(idUser).get();
        device.setUser(user);
        this.updateDevice(device);
        return device;
    }
}
