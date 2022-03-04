package com.example.twilioapi.model;

import lombok.*;

/**
 * SMS Model
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {

    private String phoneNumber; //destination number
    private String message;

}
