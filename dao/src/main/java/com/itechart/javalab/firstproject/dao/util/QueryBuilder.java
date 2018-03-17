package com.itechart.javalab.firstproject.dao.util;

import com.itechart.javalab.firstproject.entities.Contact;

import java.util.Date;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class QueryBuilder {

    public StringBuilder getWhereClause(Contact contact, Date lowerLimit, Date upperLimit) {
        StringBuilder query = new StringBuilder("");
        if (contact.getFirstName() != null) {
            query.append("c.first_name = ? and ");
        }
        if (contact.getLastName() != null) {
            query.append("c.last_name = ? and ");
        }
        if (contact.getMiddleName() != null) {
            query.append("c.middle_name = ? and ");
        }
        if (contact.getGender() != null) {
            query.append("c.gender = ? and ");
        }
        if (contact.getMaritalStatus() != null) {
            query.append("c.marital_status = ? and ");
        }
        if (contact.getNationality() != null) {
            query.append("c.nationality = ? and ");
        }
        if (contact.getContactGroup() != null) {
            query.append("c.contact_group = ? and ");
        }
        if (contact.getCountry() != null) {
            query.append("c.country = ? and ");
        }
        if (contact.getCity() != null) {
            query.append("c.city = ? and ");
        }
        if (contact.getStreet() != null) {
            query.append("c.street = ? and ");
        }
        if (contact.getHouseNumber() != null) {
            query.append("c.house_number = ? and ");
        }
        if (contact.getFlatNumber() != 0) {
            query.append("c.flat_number = ? and ");
        }
        if (contact.getPostcode() != 0) {
            query.append("c.postcode = ? and ");
        }
        if (lowerLimit == null && upperLimit == null) {
            return query;
        } else if (lowerLimit != null && upperLimit == null) {
            query.append("c.birth_date >= ? and ");
        } else if (lowerLimit == null){
            query.append("c.birth_date <= ? and ");
        } else {
            query.append(" c.birth_date between ? and ? and ");
        }
        return query;
    }

    public QueryBuilder where(StringBuilder builder) {
        builder.append("where ");
        return this;
    }

    public QueryBuilder limit(StringBuilder builder) {
        builder.append("limit ?, ? ");
        return this;
    }

    public QueryBuilder build(StringBuilder builder) {
        builder.append(";");
        return this;
    }
    public QueryBuilder orderBy(StringBuilder builder) {
        builder.append(" order by ? ");
        return this;
    }
}
