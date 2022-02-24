package com.example.twilioapi.service;

import com.example.twilioapi.model.SmsSender;
import com.example.twilioapi.model.TwilioConfig;
import com.example.twilioapi.model.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
@Slf4j
public class TwilioSmsSender implements SmsSender {

//    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);
    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
//            LOGGER.info("Send SMS: {}" + smsRequest);
            log.info("Send SMS: {}", smsRequest.getMessage());
        } else {
            throw new IllegalArgumentException("Phone number [" + smsRequest.getPhoneNumber() + "] not valid");
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return true;
    }
}
