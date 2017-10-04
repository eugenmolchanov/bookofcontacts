package util;

import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.entities.Photo;
import dto.PhoneDataDto;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 14.09.2017.
 */
public class Validation {
    public static boolean paginationDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = true;
        if (req.getParameter("startContactNumber") != null) {
            try {
                Long.parseLong(req.getParameter("startContactNumber"));
            } catch (Exception e) {
                result = false;
                logger.debug("startContactNumber param is not valid.", e);
            }
        }
        if (req.getParameter("quantityOfContacts") != null) {
            try {
                Long.parseLong(req.getParameter("quantityOfContacts"));
            } catch (Exception e) {
                result = false;
                logger.debug("quantityOfContacts param is not valid.", e);
            }
        }
        if (req.getAttribute("startContactNumber") != null && req.getAttribute("quantityOfContacts")!= null) {
            result = true;
        }
        return result;
    }

    public static boolean deleteContactsDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = true;
        Map<String, String[]> parameters = req.getParameterMap();
        if (parameters.containsKey("id")) {
            for (String parameter : parameters.get("id")) {
                try {
                    Long.parseLong(parameter);
                } catch (Exception e) {
                    result = false;
                    logger.debug("id param is not valid.", e);
                }
            }
        } else {
            return false;
        }
        return result;
    }

    public static Map<String, Object> createContactData(Map<String, Object> parameters, Logger logger) {
        Map<String, String> validationMessages = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        String invalid = "Not valid";
        Contact contact = new Contact();
        if (parameters.get("firstName") == null) {
            validationMessages.put("firstNameMessage", invalid);
        } else {
            String firstName = String.valueOf(parameters.get("firstName"));
            if (firstName.isEmpty() || firstName.length() > 255) {
                validationMessages.put("firstNameMessage", invalid);
            } else {
                contact.setFirstName(firstName);
            }
        }
        if (parameters.get("lastName") == null) {
            validationMessages.put("secondNameMessage", invalid);
        } else {
            String lastName = String.valueOf(parameters.get("lastName"));
            if (lastName.isEmpty() || lastName.length() > 255) {
                validationMessages.put("secondNameMessage", invalid);
            } else {
                contact.setLastName(lastName);
            }
        }
        if (parameters.get("middleName") != null) {
            String middleName = String.valueOf(parameters.get("middleName"));
            if (!middleName.isEmpty()) {
                if (middleName.length() > 255) {
                    validationMessages.put("middleNameMessage", invalid);
                } else {
                    contact.setMiddleName(middleName);
                }
            }
        }
        if (parameters.get("birthday") != null) {
            String birth = (String) parameters.get("birthday");
            if (!birth.isEmpty()) {
                try {
                    Date birthday = Date.valueOf(String.valueOf(parameters.get("birthday")));
                    if (birthday.compareTo(Date.valueOf(LocalDate.now())) == 1) {
                        validationMessages.put("birthdayMessage", invalid);
                    } else {
                        contact.setBirthday(birthday);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                    validationMessages.put("birthdayMessage", invalid);
                }
            }
        }
        if (parameters.get("gender") != null) {
            String gender = String.valueOf(parameters.get("gender"));
            if (!gender.equals("Мужчина") && !gender.equals("Женщина")) {
                validationMessages.put("genderMessage", invalid);
            } else {
                contact.setGender(gender);
            }
        }
        if (parameters.get("nationality") != null) {
            String nationality = String.valueOf(parameters.get("nationality"));
            if (!nationality.isEmpty()) {
                if (nationality.length() > 255) {
                    validationMessages.put("nationalityMessage", invalid);
                } else {
                    contact.setNationality(nationality);
                }
            }
        }
        if (parameters.get("maritalStatus") != null) {
            String maritalStatus = String.valueOf(parameters.get("maritalStatus"));
            if (!maritalStatus.equals("Не женат") && !maritalStatus.equals("Не замужем") && !maritalStatus.equals("Женат") && !maritalStatus.equals("Замужем") &&
                    !maritalStatus.equals("Состою в гражданском браке") && !maritalStatus.equals("Вдовец") && !maritalStatus.equals("Вдова")) {
                validationMessages.put("maritalStatusMessage", invalid);
            } else {
                contact.setMaritalStatus(maritalStatus);
            }
        }
        if (parameters.get("webSite") != null) {
            String webSite = String.valueOf(parameters.get("webSite"));
            if (!webSite.isEmpty()) {
                if (webSite.length() > 255) {
                    validationMessages.put("websiteMessage", invalid);
                } else {
                    contact.setWebSite(webSite);
                }
            }
        }
        if (parameters.get("email") != null) {
            String email = String.valueOf(parameters.get("email"));
            if (!email.isEmpty()) {
                if (email.length() > 255) {
                    validationMessages.put("emailMessage", invalid);
                } else {
                    contact.setEmail(email);
                }
            }
        }
        if (parameters.get("employmentPlace") != null) {
            String employmentPlace = String.valueOf(parameters.get("employmentPlace"));
            if (!employmentPlace.isEmpty()) {
                if (employmentPlace.length() > 255) {
                    validationMessages.put("employmentPlaceMessage", invalid);
                } else {
                    contact.setEmploymentPlace(employmentPlace);
                }
            }
        }
        if (parameters.get("contactGroup") != null) {
            String contactGroup = String.valueOf(parameters.get("contactGroup"));
            if (!contactGroup.equals("Семья") && !contactGroup.equals("Друзья") && !contactGroup.equals("Коллеги") && !contactGroup.equals("Соседи")) {
                validationMessages.put("contactGroupMessage", invalid);
            } else {
                contact.setContactGroup(contactGroup);
            }
        }
        if (parameters.get("country") != null) {
            String country = String.valueOf(parameters.get("country"));
            if (!country.isEmpty()) {
                if (country.length() > 255) {
                    validationMessages.put("countryMessage", invalid);
                } else {
                    contact.setCountry(country);
                }
            }
        }
        if (parameters.get("city") != null) {
            String city = String.valueOf(parameters.get("city"));
            if (!city.isEmpty()) {
                if (city.length() > 255) {
                    validationMessages.put("cityMessage", invalid);
                } else {
                    contact.setCity(city);
                }
            }
        }
        if (parameters.get("street") != null) {
            String street = String.valueOf(parameters.get("street"));
            if (!street.isEmpty()) {
                if (street.length() > 255) {
                    validationMessages.put("streetMessage", invalid);
                } else {
                    contact.setStreet(street);
                }
            }
        }
        if (parameters.get("houseNumber") != null) {
            String houseNumber = String.valueOf(parameters.get("houseNumber"));
            if (!houseNumber.isEmpty()) {
                if (houseNumber.length() > 255) {
                    validationMessages.put("houseNumberMessage", invalid);
                } else {
                    contact.setStreet(houseNumber);
                }
            }
        }
        if (parameters.get("flatNumber") != null) {
            String flatNum = String.valueOf(parameters.get("flatNumber"));
            if (!flatNum.isEmpty()) {
                try {
                    int flatNumber = Integer.valueOf(String.valueOf(parameters.get("flatNumber")));
                    if (flatNumber <= 0) {
                        validationMessages.put("flatNumberMessage", invalid);
                    } else {
                        contact.setFlatNumber(flatNumber);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                    validationMessages.put("flatNumberMessage", invalid);
                }
            }
        }
        if (parameters.get("postalIndex") != null) {
            String postcode = String.valueOf(parameters.get("postalIndex"));
            if (!postcode.isEmpty()) {
                try {
                    int postalIndex = Integer.valueOf(String.valueOf(parameters.get("postalIndex")));
                    if (postalIndex <= 0) {
                        validationMessages.put("postalIndexMessage", invalid);
                    } else {
                        contact.setPostcode(postalIndex);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                    validationMessages.put("postalIndexMessage", invalid);
                }
            }
        }
        if (parameters.get("attachments") != null) {
            try {
                Set<Attachment> attachments = (Set<Attachment>) parameters.get("attachments");
                for (Attachment attachment : attachments) {
                    if (attachment.getUuid() == null || attachment.getFileName().isEmpty()) {
                        validationMessages.put("attachmentNameMessage", invalid);
                    }
                }
                contact.setAttachments(attachments);
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("attachmentMessage", invalid);
            }
        }

        if (parameters.get("phones") != null) {
            try {
                Set<PhoneDataDto> phones = (Set<PhoneDataDto>) parameters.get("phones");
                if (phones.size() != 0) {
                    Set<Phone> contactPhones = new HashSet<>();
                    for (PhoneDataDto phone : phones) {
                        Phone contactPhone = new Phone();
                        if (phone.getCountryCode().isEmpty()) {
                            validationMessages.put("countryCodeMessage", invalid);
                        } else {
                            contactPhone.setCountryCode(phone.getCountryCode());
                        }
                        if (phone.getOperatorCode().isEmpty()) {
                            validationMessages.put("operatorCodeMessage", invalid);
                        } else {
                            contactPhone.setOperatorCode(Integer.parseInt(phone.getOperatorCode()));
                        }
                        if (phone.getNumber().isEmpty()) {
                            validationMessages.put("phoneNumberMessage", invalid);
                        } else {
                            contactPhone.setNumber(Integer.parseInt(phone.getNumber()));
                        }
                        if (phone.getType().isEmpty()) {
                            validationMessages.put("phoneTypeMessage", invalid);
                        } else {
                            contactPhone.setType(phone.getType());
                        }
                        if (phone.getComment().isEmpty()) {
                            validationMessages.put("phoneCommentMessage", invalid);
                        } else {
                            contactPhone.setComment(phone.getComment());
                        }
                        contactPhones.add(contactPhone);
                    }
                    contact.setPhones(contactPhones);
                }
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("attachmentMessage", invalid);
            }
        }

        if (parameters.get("photoFile") != null) {
            try {
                Photo photo = (Photo) parameters.get("photoFile");
                contact.setPhoto(photo);
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("photoMessage", invalid);
            }
        }
        result.put("contact", contact);
        result.put("validation", validationMessages);
        return result;
    }

    public static boolean redirectDataIsValid(HttpServletRequest req, Logger logger) {
        boolean result = false;
        String param = req.getParameter("form");
        if (param != null) {
            if (param.equals("createContact") || param.equals("contact") || param.equals("search") || param.equals("sendEmail") || param.equals("message")) {
                result = true;
            }
        }
        return result;
    }

    public static boolean searchDataIsValid(HttpServletRequest request, Logger logger) {
        boolean result = true;
        return true;
    }

    public static boolean sendEmailDataIsValid(HttpServletRequest request, Logger logger) {
        boolean result = true;
        return result;
    }

    public static boolean languageDataIsValid(HttpServletRequest request, Logger logger) {
        boolean result = false;
        String language = request.getParameter("language");
        if (language.equals("en_US") || language.equals("ru_RU") || language.equals("be_BY")) {
            result = true;
        }
        return result;
    }
}
