package com.itechart.javalab.firstproject.dao.util;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
public class Builder {

    public Builder addConditionIfExist(final String name, Object condition, StringBuilder builder) {
        if (condition != null && !condition.equals(0)) {
            switch (name) {
                case "first_name":
                    String firstNameCondition = "first_name = ? and ";
                    builder.append(firstNameCondition);
                    break;
                case "last_name":
                    String lastNameCondition = "last_name = ? and ";
                    builder.append(lastNameCondition);
                    break;
                case "middle_name":
                    String middleNameCondition = "middle_name = ? and ";
                    builder.append(middleNameCondition);
                    break;
                case "gender":
                    String genderCondition = "gender = ? and ";
                    builder.append(genderCondition);
                    break;
                case "marital_status":
                    String maritalCondition = "marital_status = ? and ";
                    builder.append(maritalCondition);
                    break;
                case "nationality":
                    String nationalityCondition = "nationality = ? and ";
                    builder.append(nationalityCondition);
                    break;
                case "contact_group":
                    String contactGroupCondition = "contact_group = ? and ";
                    builder.append(contactGroupCondition);
                    break;
                case "country":
                    String countryCondition = "country = ? and ";
                    builder.append(countryCondition);
                    break;
                case "city":
                    String cityCondition = "city = ? and ";
                    builder.append(cityCondition);
                    break;
                case "street":
                    String streetCondition = "street = ? and ";
                    builder.append(streetCondition);
                    break;
                case "house_number":
                    String houseNumberCondition = "house_number = ? and ";
                    builder.append(houseNumberCondition);
                    break;
                case "flat_number":
                    String flatNumberCondition = "flat_number = ? and ";
                    builder.append(flatNumberCondition);
                    break;
                case "postcode":
                    String postalIndexCondition = "postcode = ? and ";
                    builder.append(postalIndexCondition);
                    break;
            }
        }
        return this;
    }

    public Builder addBirthdayCondition(StringBuilder builder) {
        String birthdayCondition = "birth_date between ? and ? ";
        builder.append(birthdayCondition);
        return this;
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
        builder.append("order by ? ");
        return this;
    }
}
