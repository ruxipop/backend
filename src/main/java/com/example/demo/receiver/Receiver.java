package com.example.demo.receiver;

import com.example.demo.entities.dto.*;
import com.example.demo.security.jwt.service.*;
import com.example.demo.service.*;
import com.google.gson.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;

@Controller
public class Receiver {

    @Autowired
    private MeasurementService measurementService;
    private final SimpMessagingTemplate template;

    @Autowired
    Receiver(SimpMessagingTemplate template) {
        this.template = template;
    }


    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String payload) {
        Gson gson = new Gson();
        MeasurementDTO measurementDTO = gson.fromJson(payload, MeasurementDTO.class);
        try{
            System.out.println(" [x] Receive '" + measurementDTO + "'");

            measurementService.setEnergyToDevice(measurementDTO);
        }catch (MaxConsumptionExceededException ex){
            this.template.convertAndSend("/topic/alert." + ex.getUserId(), ex.getNotificationDTO());

        }

    }

}
