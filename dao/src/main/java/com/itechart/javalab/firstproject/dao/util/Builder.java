package com.itechart.javalab.firstproject.dao.util;

/**
 * Created by Евгений Молчанов on 11.09.2017.
 */
public class Builder {
    private String firstNameCondition = "c.firstName = ? and ";
    private String lastNameCondition = "c.lastName = ? and ";
    private String middleNameCondition = "c.middleName = ? and ";
    private String genderCondition = "c.gender = ? and ";
    private String maritalCondition = "c.maritalStatus = ? and ";
    private String nationalityCondition = "c.nationality = ? and ";
    private String cityCondition = "a.city = ? and ";
    private String streetCondition = "a.street = ? and ";
    private String houseNumberCondition = "a.houseNumber = ? and ";
    private String flatNumberCondition = "a.flatNumber = ? and ";
    private String postalIndexCondition = "a.postalIndex = ? and ";
    private String birthdayCondition = "c.birthday between ? and ? ";


    public Builder addConditionIfExist(String name, Object condition, StringBuilder builder) {
        if (condition != null && !condition.equals(0)) {
            switch (name) {
                case "firstName" :
                    builder.append(firstNameCondition);
                    break;
                case "lastName" :
                    builder.append(lastNameCondition);
                    break;
                case "middleName" :
                    builder.append(middleNameCondition);
                    break;
                case "gender" :
                    builder.append(genderCondition);
                    break;
                case "maritalStatus" :
                    builder.append(maritalCondition);
                    break;
                case "nationality" :
                    builder.append(nationalityCondition);
                    break;
                case "city" :
                    builder.append(cityCondition);
                    break;
                case "street" :
                    builder.append(streetCondition);
                    break;
                case "houseNumber" :
                    builder.append(houseNumberCondition);
                    break;
                case "flatNumber" :
                    builder.append(flatNumberCondition);
                    break;
                case "postalIndex" :
                    builder.append(postalIndexCondition);
                    break;
            }
        }
        return this;
    }
    public Builder addBirthdayCondition(StringBuilder builder) {
        builder.append(birthdayCondition);
        return this;
    }

    public Builder where(StringBuilder builder) {
        builder.append("where ");
        return this;
    }

    public Builder limit(StringBuilder builder) {
        builder.append("limit ?, ?");
        return this;
    }

    public Builder build(StringBuilder builder) {
        builder.append(";");
        return this;
    }
}
