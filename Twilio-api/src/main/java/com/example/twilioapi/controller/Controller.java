package com.example.twilioapi.controller;

import com.example.twilioapi.service.SenderService;
import com.example.twilioapi.model.SmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/send-text")
public class Controller {


    private final SenderService senderService;

    @Autowired
    public Controller(SenderService senderService) {
        this.senderService = senderService;
    }

    /**
     * @param weatherDTO SMS Object containing SMS details
     */
    @PostMapping
    public void sendSms(@Validated @RequestBody List<String> weatherDTO) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setPhoneNumber(weatherDTO.get(0));
//        smsRequest.setMessage(weatherDTO.get(1-???????)); //get 1 - [as much as I need]
//        position 0 is phone number, 1-? is message

//        StringBuilder message = new StringBuilder();
//        for (int i = 1; i < weatherDTO.size(); i++) {
//            message.append(weatherDTO.get(i));
//            if(i+1 != weatherDTO.size()){
//                message.append("\n\n");
//            }
//        }

        smsRequest.setMessage(weatherDTO.get(1));
        senderService.sendSms(smsRequest);
    }
}
