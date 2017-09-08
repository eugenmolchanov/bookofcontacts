package com.itechart.javalab.firstproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Евгений Молчанов on 06.09.2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private long id;
    private String city;
    private String street;
    private int houseNumber;
    private int flatNumber;
    private int postalIndex;
}
