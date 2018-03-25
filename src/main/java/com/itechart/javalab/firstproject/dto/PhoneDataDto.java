package com.itechart.javalab.firstproject.dto;

import lombok.Data;

/**
 * Created by Yauhen Malchanau on 03.10.2017.
 */
@Data
public class PhoneDataDto {
    private String id;
    private String countryCode;
    private String operatorCode;
    private String number;
    private String type;
    private String comment;
    private String contactId;
}
