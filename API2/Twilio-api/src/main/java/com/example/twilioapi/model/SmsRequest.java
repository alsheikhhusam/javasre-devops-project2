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

//    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber,
//                      @JsonProperty("message") String message) {
//        this.phoneNumber = phoneNumber;
//        this.message = message;

}
