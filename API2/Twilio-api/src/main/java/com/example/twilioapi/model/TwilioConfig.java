package com.example.twilioapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Model for Twilio to obtain User parameters
 */
@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@Getter @Setter
public class TwilioConfig {

    private String accountSid;
    private String authToken;
    private String trialNumber;

}
