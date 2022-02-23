package com.example.twilioapi.service;

import com.example.twilioapi.model.SmsRequest;
import com.example.twilioapi.model.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private final SmsSender smsSender;

    @Autowired
    public SenderService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    /**
     * @param smsRequest sends SMS
     */
    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
}
