package com.itechart.javalab.firstproject.dao.util;

import com.itechart.javalab.firstproject.entities.Contact;

import java.util.Date;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class Builder {

    public StringBuilder getWhereClause(Contact contact, Date lowerLimit, Date upperLimit) {
        StringBuilder query = new StringBuilder("");
        if (contact.getFirstName() != null) {
            query.append("first_name = ? and ");
        }
        if (contact.getLastName() != null) {
            query.append("last_name = ? and ");
        }
        if (contact.getMiddleName() != null) {
            query.append("middle_name = ? and ");
        }
        if (contact.getGender() != null) {
            query.append("gender = ? and ");
        }
        if (contact.getMaritalStatus() != null) {
            query.append("marital_status = ? and ");
        }
        if (contact.getNationality() != null) {
            query.append("nationality = ? and ");
        }
        if (contact.getContactGroup() != null) {
            query.append("contact_group = ? and ");
        }
        if (contact.getCountry() != null) {
            query.append("country = ? and ");
        }
        if (contact.getCity() != null) {
            query.append("city = ? and ");
        }
        if (contact.getStreet() != null) {
            query.append("street = ? and ");
        }
        if (contact.getHouseNumber() != null) {
            query.append("house_number = ? and ");
        }
        if (contact.getFlatNumber() != 0) {
            query.append("flat_number = ? and ");
        }
        if (contact.getPostcode() != 0) {
            query.append("postcode = ? and ");
        }
        if (lowerLimit == null && upperLimit == null) {
            return query;
        } else if (lowerLimit != null && upperLimit == null) {
            query.append("birth_date >= ? and ");
        } else if (lowerLimit == null){
            query.append("birth_date <= ? and ");
        } else {
            query.append(" and birth_date between ? and ? and ");
        }
        return query;
    }


    public Builder where(StringBuilder builder) {
        builder.append("where ");
        return this;
    }

    public Builder limit(StringBuilder builder) {
        builder.append("limit ?, ? ");
        return this;
    }

    public Builder build(StringBuilder builder) {
        builder.append(";");
        return this;
    }
    public Builder orderBy(StringBuilder builder) {
        builder.append(" order by ? ");
        return this;
    }
}
