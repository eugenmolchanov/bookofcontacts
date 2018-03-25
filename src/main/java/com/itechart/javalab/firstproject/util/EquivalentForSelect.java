package com.itechart.javalab.firstproject.util;

import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.resources.MessageManager;

import java.util.Set;

/**
 * Created by Yauhen Malchanau on 22.10.2017.
 */
public class EquivalentForSelect {
    public static void fill(Set<Contact> contacts) {
        for (Contact contact : contacts) {
            if (contact.getGender() != null) {
                contact.setGender(MessageManager.getProperty(contact.getGender()));
            }
            if (contact.getMaritalStatus() != null) {
                contact.setMaritalStatus(MessageManager.getProperty(contact.getMaritalStatus()));
            }
            if (contact.getContactGroup() != null) {
                contact.setContactGroup(MessageManager.getProperty(contact.getContactGroup()));
            }
        }
    }

    public static void fill(Contact contact) {
        if (contact.getGender() != null) {
            contact.setGender(MessageManager.getProperty(contact.getGender()));
        }
        if (contact.getMaritalStatus() != null) {
            contact.setMaritalStatus(MessageManager.getProperty(contact.getMaritalStatus()));
        }
        if (contact.getContactGroup() != null) {
            contact.setContactGroup(MessageManager.getProperty(contact.getContactGroup()));
        }
        if (contact.getPhones() != null) {
            for (Phone phone : contact.getPhones()) {
                phone.setType(MessageManager.getProperty(phone.getType()));
            }
        }
    }
}
