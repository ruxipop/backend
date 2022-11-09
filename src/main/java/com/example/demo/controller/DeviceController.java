package com.example.demo.controller;

import com.example.demo.entities.*;
import com.example.demo.service.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

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
    public ResponseEntity<List<Device>> getDevices() {
        List<Device> deviceList = this.deviceService.getAllDevices();
        return new ResponseEntity<>(deviceList, HttpStatus.OK);
    }


    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUserByID(@PathVariable Integer id) {
        this.measurementService.deleteMeasurement(id);
        this.deviceService.deleteDevice(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Integer addDevice(@RequestBody List<String> device) {
        return this.deviceService.addDevice(device).getId();
    }


    @PostMapping("/updateDevice/{idOwner}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateDevice(@PathVariable Integer idOwner, @RequestBody Map<?, ?> device) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(device);
        this.deviceService.update(idOwner, (new ObjectMapper().readValue(jsonObject.toString(), Device.class)));
    }


    @PostMapping("/addUserToDevice/{idUser}/{idDevice}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addUserToDevice(@PathVariable Integer idUser, @PathVariable Integer idDevice) {
        Device device = this.deviceService.addUserToDevice(idDevice, idUser);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() ->
                measurementService.setEnergyToDevice(device), 0, 1, TimeUnit.HOURS);
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
    public Device getDeviceById(@PathVariable Integer idDevice) {
        return deviceService.getDeviceById(idDevice);
    }

    @GetMapping(value = "/findUser/{idDevice}")
    public Integer getDeviceUser(@PathVariable Integer idDevice) {
        Device device = deviceService.getDeviceById(idDevice);
        return device.getUser().getId();
    }
}
