package com.example.twilioapi.model;

/**
 * Interface for SMS sending function
 */
public interface SmsSender {
    void sendSms(SmsRequest smsRequest);
}
