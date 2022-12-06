package com.example.demo;

import com.example.demo.receiver.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.concurrent.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException {


        SpringApplication.run(DemoApplication.class, args);
//        Receiver.receive();
    }

}
