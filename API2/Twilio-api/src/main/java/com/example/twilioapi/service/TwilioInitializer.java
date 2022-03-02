package com.example.twilioapi.service;

import com.example.twilioapi.model.TwilioConfig;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {


    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
    private final TwilioConfig twilioConfig;

    /**
     * @param twilioConfig Initializes Twilio to take in authentication credentials
     */
    @Autowired
    public TwilioInitializer(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(
                twilioConfig.getAccountSid(),
                twilioConfig.getAuthToken()
        );
        //Logs that show account SID from Twilio
        LOGGER.info("Twilio initialized with account {} " + twilioConfig.getAccountSid());
    }
}
