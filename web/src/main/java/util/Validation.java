package util;

import com.itechart.javalab.firstproject.entities.*;
import dto.AttachmentDataDto;
import dto.PhoneDataDto;
import org.apache.log4j.Logger;
import resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

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
        if (req.getAttribute("startContactNumber") != null && req.getAttribute("quantityOfContacts") != null) {
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

    public static boolean deleteMessagesDataIsValid(HttpServletRequest req, Logger logger) {
        return deleteContactsDataIsValid(req, logger);
    }

    public static Map<String, Object> createContactData(Map<String, Object> parameters, Logger logger) {
        Map<String, String> validationMessages = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        String invalid = "invalid";
        Contact contact = new Contact();
        if (parameters.get("firstName") == null) {
            validationMessages.put("firstNameMessage", MessageManager.getProperty("must_be_filled"));
        } else {
            String firstName = String.valueOf(parameters.get("firstName"));
            if (firstName.isEmpty() || Pattern.compile("[\\d~@#$%^&*.()_+|>?/<\":}!№;,\\s]").matcher(firstName).find()) {
                validationMessages.put("firstNameMessage", MessageManager.getProperty("letters_validation"));
            } else if (firstName.length() > 255) {
                validationMessages.put("firstNameMessage", MessageManager.getProperty("size_255"));
            } else {
                contact.setFirstName(firstName);
            }
        }
        if (parameters.get("lastName") == null) {
            validationMessages.put("secondNameMessage", MessageManager.getProperty("must_be_filled"));
        } else {
            String lastName = String.valueOf(parameters.get("lastName"));
            if (lastName.isEmpty() || Pattern.compile("[\\d~@#$%^&*.()_+|>?/<\":}!№;,\\s]").matcher(lastName).find()) {
                validationMessages.put("secondNameMessage", MessageManager.getProperty("letters_validation"));
            } else if (lastName.length() > 255) {
                validationMessages.put("secondNameMessage", MessageManager.getProperty("size_255"));
            } else {
                contact.setLastName(lastName);
            }
        }
        if (parameters.get("middleName") != null) {
            String middleName = String.valueOf(parameters.get("middleName"));
            if (!middleName.isEmpty()) {
                if (Pattern.compile("[\\d~@#$%^&*.()_+|>?/<\":}!№;,\\s]").matcher(middleName).find()) {
                    validationMessages.put("middleNameMessage", MessageManager.getProperty("letters_validation"));
                } else if (middleName.length() > 255) {
                    validationMessages.put("middleNameMessage", MessageManager.getProperty("size_255"));
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
                        validationMessages.put("birthdayMessage", MessageManager.getProperty("invalid_birthday"));
                    } else {
                        contact.setBirthday(birthday);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                    validationMessages.put("birthdayMessage", MessageManager.getProperty("invalid_birthday"));
                }
            }
        }
        if (parameters.get("gender") != null) {
            String gender = String.valueOf(parameters.get("gender"));
            if (!gender.isEmpty()) {
                if (!gender.equals("Мужчина") && !gender.equals("Женщина")) {
                    validationMessages.put("genderMessage", MessageManager.getProperty("choose_from_set"));
                } else {
                    contact.setGender(gender);
                }
            }
        }
        if (parameters.get("nationality") != null) {
            String nationality = String.valueOf(parameters.get("nationality"));
            if (!nationality.isEmpty()) {
                if (Pattern.compile("[\\d~@#$%^&*.()_+|>?/<\":}!№;,]").matcher(nationality).find()) {
                    validationMessages.put("nationalityMessage", MessageManager.getProperty("letters_validation"));
                } else if (nationality.length() > 255) {
                    validationMessages.put("nationalityMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setNationality(nationality);
                }
            }
        }
        if (parameters.get("maritalStatus") != null) {
            String maritalStatus = String.valueOf(parameters.get("maritalStatus"));
            if (!maritalStatus.isEmpty()) {
                if (!maritalStatus.equals("Не женат") && !maritalStatus.equals("Не замужем") && !maritalStatus.equals("Женат") && !maritalStatus.equals("Замужем") &&
                        !maritalStatus.equals("Состою в гражданском браке") && !maritalStatus.equals("Вдовец") && !maritalStatus.equals("Вдова")) {
                    validationMessages.put("maritalStatusMessage", MessageManager.getProperty("choose_from_set"));
                } else {
                    contact.setMaritalStatus(maritalStatus);
                }
            }
        }
        if (parameters.get("webSite") != null) {
            String webSite = String.valueOf(parameters.get("webSite"));
            if (!webSite.isEmpty()) {
                if (!Pattern.compile("(http(s)?://.)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z\u200C\u200B]{2,6}\\b([-a-zA-Z0-9\u200C\u200B@:%_+.~#?&=]*)").matcher(webSite).matches()) {
                    validationMessages.put("websiteMessage", MessageManager.getProperty("invalid_url"));
                } else if (webSite.length() > 255) {
                    validationMessages.put("websiteMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setWebSite(webSite);
                }
            }
        }
        if (parameters.get("email") != null) {
            String email = String.valueOf(parameters.get("email"));
            if (!email.isEmpty()) {
                if (!Pattern.compile("(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$").matcher(email).matches()) {
                    validationMessages.put("emailMessage", MessageManager.getProperty("invalid_email"));
                } else if (email.length() > 255) {
                    validationMessages.put("emailMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setEmail(email);
                }
            }
        }
        if (parameters.get("employmentPlace") != null) {
            String employmentPlace = String.valueOf(parameters.get("employmentPlace"));
            if (!employmentPlace.isEmpty()) {
                if (Pattern.compile("[~@#$%^&/.*()_+|?><:}!№;,]").matcher(employmentPlace).find()) {
                    validationMessages.put("employmentPlaceMessage", MessageManager.getProperty("letter_and_digit_validation"));
                } else if (employmentPlace.length() > 255) {
                    validationMessages.put("emailMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setEmploymentPlace(employmentPlace);
                }
            }
        }
        if (parameters.get("contactGroup") != null) {
            String contactGroup = String.valueOf(parameters.get("contactGroup"));
            if (!contactGroup.isEmpty()) {
                if (!contactGroup.equals("Семья") && !contactGroup.equals("Друзья") && !contactGroup.equals("Коллеги") && !contactGroup.equals("Соседи")) {
                    validationMessages.put("contactGroupMessage", MessageManager.getProperty("choose_from_set"));
                } else {
                    contact.setContactGroup(contactGroup);
                }
            }
        }
        if (parameters.get("country") != null) {
            String country = String.valueOf(parameters.get("country"));
            if (!country.isEmpty()) {
                if (Pattern.compile("[\\d~@#$%^&*.()_+|>?/<\":}!№;,]").matcher(country).find()) {
                    validationMessages.put("countryMessage", MessageManager.getProperty("letters_validation"));
                } else if (country.length() > 255) {
                    validationMessages.put("countryMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setCountry(country);
                }
            }
        }
        if (parameters.get("city") != null) {
            String city = String.valueOf(parameters.get("city"));
            if (!city.isEmpty()) {
                if (Pattern.compile("[\\d~@#$%^&*.()_+|>?/<\":}!№;,]").matcher(city).find()) {
                    validationMessages.put("cityMessage", MessageManager.getProperty("letters_validation"));
                } else if (city.length() > 255) {
                    validationMessages.put("cityMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setCity(city);
                }
            }
        }
        if (parameters.get("street") != null) {
            String street = String.valueOf(parameters.get("street"));
            if (!street.isEmpty()) {
                if (Pattern.compile("[~@#$%^&*.()_+|>?/<\":}!№;,]").matcher(street).find()) {
                    validationMessages.put("streetMessage", MessageManager.getProperty("letter_and_digit_validation"));
                } else if (street.length() > 255) {
                    validationMessages.put("streetMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setStreet(street);
                }
            }
        }
        if (parameters.get("houseNumber") != null) {
            String houseNumber = String.valueOf(parameters.get("houseNumber"));
            if (!houseNumber.isEmpty()) {
                if (Pattern.compile("[~@#$%^&*.()_+|>?/<\":}!№;,\\s]").matcher(houseNumber).find()) {
                    validationMessages.put("houseNumberMessage", MessageManager.getProperty("letter_and_digit_validation"));
                } else if (houseNumber.length() > 255) {
                    validationMessages.put("houseNumberMessage", MessageManager.getProperty("size_255"));
                } else {
                    contact.setHouseNumber(houseNumber);
                }
            }
        }
        if (parameters.get("flatNumber") != null) {
            String flatNum = String.valueOf(parameters.get("flatNumber"));
            if (!flatNum.isEmpty()) {
                if (Pattern.compile("[^\\d]").matcher(flatNum).find()) {
                    validationMessages.put("flatNumberMessage", MessageManager.getProperty("digit_validation"));
                } else {
                    try {
                        int flatNumber = Integer.valueOf(String.valueOf(parameters.get("flatNumber")));
                        if (flatNumber <= 0) {
                            validationMessages.put("flatNumberMessage", MessageManager.getProperty("digit"));
                        } else {
                            contact.setFlatNumber(flatNumber);
                        }
                    } catch (Exception e) {
                        logger.debug(e.getMessage(), e);
                        validationMessages.put("flatNumberMessage", MessageManager.getProperty("digit_validation"));
                    }
                }
            }
        }
        if (parameters.get("postalIndex") != null) {
            String postcode = String.valueOf(parameters.get("postalIndex"));
            if (!postcode.isEmpty()) {
                if (Pattern.compile("[^\\d]").matcher(postcode).find()) {
                    validationMessages.put("postalIndexMessage", MessageManager.getProperty("digit_validation"));
                } else {
                    try {
                        int postalIndex = Integer.valueOf(String.valueOf(parameters.get("postalIndex")));
                        if (postalIndex <= 0) {
                            validationMessages.put("postalIndexMessage", MessageManager.getProperty("digit"));
                        } else {
                            contact.setPostcode(postalIndex);
                        }
                    } catch (Exception e) {
                        logger.debug(e.getMessage(), e);
                        validationMessages.put("postalIndexMessage", MessageManager.getProperty("digit_validation"));
                    }
                }
            }
        }
        if (parameters.get("attachments") != null) {
            try {
                Set<AttachmentDataDto> attachments = (Set<AttachmentDataDto>) parameters.get("attachments");
                if (attachments.size() != 0) {
                    Set<Attachment> contactAttachments = new HashSet<>();
                    for (AttachmentDataDto attachment : attachments) {
                        Attachment contactAttachment = new Attachment();
                        if (attachment.getId() != null) {
                            if (attachment.getId().isEmpty()) {
                                validationMessages.put("attachmentInvalidMessage", MessageManager.getProperty("invalid_attachment_data"));
                            } else {
                                try {
                                    long attachmentId = Long.parseLong(attachment.getId());
                                    contactAttachment.setId(attachmentId);
                                } catch (Exception e) {
                                    logger.debug(e.getMessage(), e);
                                    validationMessages.put("attachmentInvalidMessage", MessageManager.getProperty("invalid_attachment_data"));
                                }
                            }
                        }
                        if (attachment.getId() == null && attachment.getPathToFile() == null) {
                            validationMessages.put("addAttachMessage", MessageManager.getProperty("add_attachment"));
                        }
                        if (attachment.getFileName().isEmpty() || attachment.getFileName().length() > 255) {
                            validationMessages.put("attachTitleMessage", MessageManager.getProperty("invalid_attach_filename"));
                        } else {
                            contactAttachment.setFileName(attachment.getFileName());
                        }
                        if (attachment.getCommentary().length() > 255) {
                            validationMessages.put("attachCommentMessage", MessageManager.getProperty("invalid_attachment_comment"));
                        } else if (!attachment.getCommentary().isEmpty()) {
                            contactAttachment.setCommentary(attachment.getCommentary());
                        }
                        if (contactAttachment.getId() == 0) {
                            contactAttachment.setDate(attachment.getDate());
                            contactAttachment.setPathToFile(attachment.getPathToFile());
                            contactAttachment.setUuid(attachment.getUuid());
                        }
                        contactAttachments.add(contactAttachment);
                    }
                    contact.setAttachments(contactAttachments);
                }
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("attachmentMessage", MessageManager.getProperty("invalid_attachment_data"));
            }
        }

        if (parameters.get("phones") != null) {
            try {
                Set<PhoneDataDto> phones = (Set<PhoneDataDto>) parameters.get("phones");
                if (phones.size() != 0) {
                    Set<Phone> contactPhones = new HashSet<>();
                    for (PhoneDataDto phone : phones) {
                        Phone contactPhone = new Phone();
                        if (phone.getId() != null) {
                            if (phone.getId().isEmpty()) {
                                validationMessages.put("phoneInvalidMessage", MessageManager.getProperty("invalid_phone_data"));
                            } else {
                                try {
                                    long phoneId = Long.parseLong(phone.getId());
                                    contactPhone.setId(phoneId);
                                } catch (Exception e) {
                                    logger.debug(e.getMessage(), e);
                                    validationMessages.put("phoneInvalidMessage", MessageManager.getProperty("invalid_phone_data"));
                                }
                            }
                        }
                        if (phone.getCountryCode().isEmpty() || Pattern.compile("[^\\d]").matcher(phone.getCountryCode()).find()) {
                            validationMessages.put("countryCodeMessage", MessageManager.getProperty("invalid_phone_country_code"));
                        } else {
                            contactPhone.setCountryCode(phone.getCountryCode());
                        }
                        if (phone.getOperatorCode().isEmpty() || Pattern.compile("[^\\d]").matcher(phone.getOperatorCode()).find()) {
                            validationMessages.put("operatorCodeMessage", MessageManager.getProperty("invalid_phone_operator_code"));
                        } else {
                            contactPhone.setOperatorCode(Integer.parseInt(phone.getOperatorCode()));
                        }
                        if (phone.getNumber().isEmpty() || Pattern.compile("[^\\d]").matcher(phone.getNumber()).find()) {
                            validationMessages.put("phoneNumberMessage", MessageManager.getProperty("invalid_phone_number"));
                        } else {
                            contactPhone.setNumber(Integer.parseInt(phone.getNumber()));
                        }
                        if (phone.getType().isEmpty() || (!phone.getType().equals("Рабочий") && !phone.getType().equals("Домашний") && !phone.getType().equals("Сотовый"))) {
                            validationMessages.put("phoneTypeMessage", MessageManager.getProperty("invalid_phone_type"));
                        } else {
                            contactPhone.setType(phone.getType());
                        }
                        if (phone.getComment().length() > 255) {
                            validationMessages.put("phoneCommentMessage", MessageManager.getProperty("invalid_phone_comment"));
                        } else if (!phone.getComment().isEmpty()) {
                            contactPhone.setComment(phone.getComment());
                        }
                        contactPhones.add(contactPhone);
                    }
                    contact.setPhones(contactPhones);
                }
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("attachmentMessage",  MessageManager.getProperty("invalid_phone_data"));
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

    public static Map<String, Object> editContactData(Map<String, Object> parameters, Logger logger) {
        Map<String, Object> result = createContactData(parameters, logger);
        String invalid = "Not valid";
        Set<Long> attachmentsForDelete = new HashSet<>();
        Set<Long> phonesForDelete = new HashSet<>();
        Map<String, String> validationMessages = (Map<String, String>) result.get("validation");
        Contact contact = (Contact) result.get("contact");
        if (parameters.get("contactId") != null) {
            String contactId = String.valueOf(parameters.get("contactId"));
            if (!contactId.isEmpty()) {
                try {
                    long id = Long.valueOf(String.valueOf(parameters.get("contactId")));
                    if (id < 1) {
                        validationMessages.put("contactMessage", invalid);
                    } else {
                        contact.setId(id);
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                    validationMessages.put("contactMessage", invalid);
                }
            }
        }
        if (parameters.get("attachmentsForDelete") != null) {
            try {
                Set<String> attachmentIds = (Set<String>) parameters.get("attachmentsForDelete");
                if (attachmentIds.size() != 0) {
                    for (String attachmentId : attachmentIds) {
                        if (attachmentId.isEmpty()) {
                            validationMessages.put("attachmentMessage", invalid);
                        } else {
                            try {
                                long id = Long.parseLong(attachmentId);
                                attachmentsForDelete.add(id);
                            } catch (Exception e) {
                                logger.debug(e.getMessage(), e);
                                validationMessages.put("attachmentMessage", invalid);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("attachmentMessage", invalid);
            }
            result.put("attachmentsForDelete", attachmentsForDelete);
        }
        if (parameters.get("phonesForDelete") != null) {
            try {
                Set<String> phonesIds = (Set<String>) parameters.get("phonesForDelete");
                if (phonesIds.size() != 0) {
                    for (String phoneId : phonesIds) {
                        if (phoneId.isEmpty()) {
                            validationMessages.put("phoneMessage", invalid);
                        } else {
                            try {
                                long id = Long.parseLong(phoneId);
                                phonesForDelete.add(id);
                            } catch (Exception e) {
                                logger.debug(e.getMessage(), e);
                                validationMessages.put("phoneMessage", invalid);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.debug(e.getMessage(), e);
                validationMessages.put("phoneMessage", invalid);
            }
            result.put("phonesForDelete", phonesForDelete);
        }
        result.replace("validation", validationMessages);
        if (contact.getId() != 0) {
            result.replace("contact", contact);
        }
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

    public static Map<String, String> sendEmailDataIsValid(HttpServletRequest request, Logger logger) {
        Map<String, String> validationMessages = new HashMap<>();
        String emails = request.getParameter("addressees");
        if (emails == null || emails.isEmpty()) {
            validationMessages.put("addresseesMessage", MessageManager.getProperty("must_be_filled"));
        } else {
            String [] emailArray = emails.split("\\s");
            for (String email : emailArray) {
                if (!Pattern.compile("(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$").matcher(email).matches()) {
                    validationMessages.put("addresseesMessage", MessageManager.getProperty("invalid_email"));
                }
            }
        }
        String topic = request.getParameter("topic");
        if (topic != null && topic.length() > 255) {
            validationMessages.put("topicMessage", MessageManager.getProperty("size_255"));
        }
        String textMessage = request.getParameter("message");
        if (textMessage == null || textMessage.isEmpty()) {
            validationMessages.put("textMessage", MessageManager.getProperty("must_be_filled"));
        }
        return validationMessages;
    }

    public static boolean languageDataIsValid(HttpServletRequest request, Logger logger) {
        boolean result = false;
        String language = request.getParameter("language");
        if (Objects.equals(language, "en_US") || Objects.equals(language, "ru_RU") || Objects.equals(language, "be_BY")) {
            result = true;
        }
        return result;
    }
}
