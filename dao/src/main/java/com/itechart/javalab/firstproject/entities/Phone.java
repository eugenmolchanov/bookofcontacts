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
public class Phone {
    private long id;
    private int countryCode;
    private int operatorCode;
    private long number;
    private String type;
    private String comment;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Phone) {
            Phone phone = (Phone) obj;
            return this.countryCode == phone.countryCode && this.operatorCode == phone.operatorCode && this.number == phone.number && this.type.equals(phone.type);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + countryCode;
        result = result * 31 + operatorCode;
        result = result * 31 + (int) number;
        if (type != null) {result = result * 31 + type.hashCode();}
        return result;
    }
}
