package com.itechart.javalab.firstproject.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Created by Yauhen Malchanau on 06.09.2017.
 */
@Data
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
public class Phone {
    private long id;
    private String countryCode;
    private int operatorCode;
    private long number;
    private String type;
    private String comment;
    private long contactId;
}
