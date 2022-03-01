package com.example.twilioapi.model;

import com.example.twilioapi.model.SmsRequest;

/**
 * Interface for SMS sending function
 */
public interface SmsSender {

    void sendSms(SmsRequest smsRequest);
}
