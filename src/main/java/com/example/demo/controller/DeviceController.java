package com.example.demo.controller;

import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@RequestMapping("/device")
@CrossOrigin
@RestController
public class DeviceController {


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MeasurementService measurementService;


    @GetMapping("/getAllDevice")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<DeviceDTO> getDevices() {
        List<Device> deviceList = this.deviceService.getAllDevices();

        return deviceList.stream().map(DeviceDTO::convertTODTO).collect(Collectors.toList());
    }


    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUserByID(@PathVariable Integer id) {

        this.deviceService.deleteDevice(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addDevice(@RequestBody DeviceDTO deviceDTO) throws IOException {
        System.out.println("add "+deviceDTO);
        Device device = deviceDTO.convertTO();
        this.deviceService.addDevice(device);
//        if (deviceDTO.getUser() != null) {
//            Runtime.getRuntime().exec("java -jar /Users/popruxi/Desktop/demo/src/main/resources/sd-desktop.jar "+device.getId());
//        }
    }


    @PostMapping("/updateDevice/{idOwner}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateDevice(@PathVariable Integer idOwner, @RequestBody DeviceDTO deviceDTO) throws IOException {
        Device device = deviceDTO.convertTO();
        this.deviceService.update(idOwner, device);
//        if(idOwner!=null) {
//            Runtime.getRuntime().exec("java -jar /Users/popruxi/Desktop/demo/src/main/resources/sd-desktop.jar "+device.getId());
//        }
        }

    @PostMapping("/findUserByID/{idUser}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User findUserByID(@PathVariable Integer idUser) {
        return this.deviceService.addUserToDevice(idUser);
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsers() {
        return this.deviceService.getUsers();
    }

    @GetMapping("/getUserWithoutDevices")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsersWithoutDevices() {
        return deviceService.getUsersWithoutDevices();
    }

    @GetMapping(value = "/findOwner/{idDevice}")
    public User findOwner(@PathVariable Integer idDevice) {
        return deviceService.findOwner(idDevice);
    }

    @GetMapping(value = "/byID/{idDevice}")
    public DeviceDTO getDeviceById(@PathVariable Integer idDevice) {
        return DeviceDTO.convertTODTO(deviceService.getDeviceById(idDevice));
    }

    @GetMapping(value = "/findUser/{idDevice}")
    public Integer getDeviceUser(@PathVariable Integer idDevice) {
        Device device = deviceService.getDeviceById(idDevice);
        return device.getUser().getId();
    }
}
