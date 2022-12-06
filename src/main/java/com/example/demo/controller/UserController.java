package com.example.demo.controller;

import com.example.demo.entities.*;
import com.example.demo.entities.dto.*;
import com.example.demo.payload.*;
import com.example.demo.security.jwt.*;
import com.example.demo.security.jwt.service.*;
import com.example.demo.service.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.*;

@RequestMapping("/user")
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private NotificationService notificationService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles.get(0)));
    }


    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsers() {
        return this.userService.getAll();
    }

    @GetMapping("/getAllByRole")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsersByRole() {
        return this.userService.getAllByRole("ROLE_USER");
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addUser(@RequestBody UserDTO user) {
        userService.addUser(user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
    }


    @GetMapping(value = "/getDevice/{username}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Device getDevice(@PathVariable String username) {
        return userService.getDeviceOfGivenOwner(username);
    }


    @DeleteMapping(value = "{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUserByUsername(@PathVariable String username) {
        notificationService.deleteNotificationByUsername(username);
        userService.deleteUserByUsername(username);

    }

    @GetMapping(value = "{username}")
    public User getUserByUsername(@PathVariable String username) {
        return this.userService.getUserByUsername(username);
    }

    @GetMapping(value = "/getNotifications/{userID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<NotificationDTO> getNotification(@PathVariable Integer userID) {
        return notificationService.getNotification(userID);
    }

    @DeleteMapping(value="/deleteNotification/{notID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void deleteNotification(@PathVariable Integer notID){
        notificationService.deleteNotification(notID);
    }

}
