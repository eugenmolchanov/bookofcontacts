package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.dao.ContactDao;
import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.entities.Contact;
import com.itechart.javalab.firstproject.entities.Phone;
import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.service.impl.AttachmentServiceImpl;
import com.itechart.javalab.firstproject.service.impl.ContactServiceImpl;
import com.itechart.javalab.firstproject.service.impl.PhoneServiceImpl;
import com.itechart.javalab.firstproject.service.impl.PhotoServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Yauhen Malchanau on 31.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {
    @InjectMocks
    private ContactService contactService = ContactServiceImpl.getInstance();
    @InjectMocks
    private AttachmentService attachmentService = AttachmentServiceImpl.getInstance();
    @InjectMocks
    private PhotoService photoService = PhotoServiceImpl.getInstance();
    @InjectMocks
    private PhoneService phoneService = PhoneServiceImpl.getInstance();

    @Mock
    private ContactDao contactDao;
    @Mock
    private AttachmentDao attachmentDao;
    @Mock
    private PhotoDao photoDao;
    @Mock
    private PhoneDao phoneDao;
    @Mock
    private Contact contact;
    @Mock
    private Photo photo;

    @After
    public void after() {
        reset(contactDao, photoDao, attachmentDao, phoneDao, contact, photo);
    }

    @Test
    public void shouldDelete() throws SQLException {
        doNothing().when(contactDao).delete(anyLong(), any(Connection.class));

        contactService.delete(eq(anyLong()));
        verify(contactDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeletingContact() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).delete(anyLong(), any(Connection.class));

        contactService.delete(eq(anyLong()));
        verify(contactDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldCreate() throws Exception {
        when(contact.getPhoto())
                .thenReturn(photo);
        when(contact.getAttachments())
                .thenReturn(getAttachmentsForSave());
        when(contact.getPhones())
                .thenReturn(getPhonesForSave());
        when(contactDao.save(any(Contact.class), any(Connection.class)))
                .thenReturn(1L);
        when(attachmentDao.save(any(Attachment.class), any(Connection.class)))
                .thenReturn(1L);
        when(photoDao.save(any(Photo.class), any(Connection.class)))
                .thenReturn(1L);
        when(phoneDao.save(any(Phone.class), any(Connection.class)))
                .thenReturn(1L);

        contactService.create(contact);
        verify(contactDao).save(eq(contact), any(Connection.class));
        verify(attachmentDao, times(2)).save(any(Attachment.class), any(Connection.class));
        verify(photoDao).save(any(Photo.class), any(Connection.class));
        verify(phoneDao, times(2)).save(any(Phone.class), any(Connection.class));
        verifyNoMoreInteractions(contactDao, attachmentDao, photoDao, phoneDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenCreateContact() throws Exception {
        when(contact.getPhoto())
                .thenReturn(photo);
        when(photoDao.save(any(Photo.class), any(Connection.class)))
                .thenReturn(1L);
        doThrow(new SQLException())
                .when(contactDao).save(any(Contact.class), any(Connection.class));

        contactService.create(contact);
        verify(photoDao).save(any(Photo.class), any(Connection.class));
        verify(contactDao).save(any(Contact.class), any(Connection.class));
        verifyNoMoreInteractions(photoDao, contactDao);
    }

    @Test
    public void shouldFindById() throws SQLException {
        when(contactDao.findById(anyLong(), any(Connection.class)))
                .thenReturn(contact);

        contactService.findById(eq(anyLong()));
        verify(contactDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenFindById() throws SQLException {
        doThrow(new SQLException()).when(contactDao).findById(anyLong(), any(Connection.class));

        contactService.findById(eq(anyLong()));
        verify(contactDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldUpdate() throws SQLException {
        doNothing()
                .when(contactDao).update(any(Contact.class), any(Connection.class));
        when(attachmentDao.getAllAttachmentsOfContact(anyLong(), any(Connection.class)))
                .thenReturn(getAttachments());
        doNothing()
                .when(attachmentDao).update(any(Attachment.class), any(Connection.class));
        when(attachmentDao.save(any(Attachment.class), any(Connection.class)))
                .thenReturn(1L);
        doNothing()
                .when(attachmentDao).delete(anyLong(), any(Connection.class));
        when(phoneDao.getAllPhonesOfContact(anyLong(), any(Connection.class)))
                .thenReturn(getPhones());
        doNothing()
                .when(phoneDao).update(any(Phone.class), any(Connection.class));
        when(phoneDao.save(any(Phone.class), any(Connection.class)))
                .thenReturn(1L);
        doNothing()
                .when(phoneDao).delete(anyLong(), any(Connection.class));
        doNothing()
                .when(photoDao).update(any(Photo.class), any(Connection.class));
        when(contact.getPhoto())
                .thenReturn(photo);
        when(contact.getPhoto().getId())
                .thenReturn(1L);
        when(contact.getPhoto().getPathToFile())
                .thenReturn("path");
        when(contact.getAttachments())
                .thenReturn(getAttachmentsForSave());
        when(contact.getPhones())
                .thenReturn(getPhonesForSave());

        Set<Long> phonesForDelete = new HashSet<>();
        phonesForDelete.add(1L);
        Set<Long> attachmentsForDelete = new HashSet<>();
        attachmentsForDelete.add(1L);
        contactService.update(contact, phonesForDelete, attachmentsForDelete);
        verify(contactDao).update(any(Contact.class), any(Connection.class));
        verify(attachmentDao).getAllAttachmentsOfContact(anyLong(), any(Connection.class));
        verify(attachmentDao).update(any(Attachment.class), any(Connection.class));
        verify(attachmentDao, times(1)).save(any(Attachment.class), any(Connection.class));
        verify(attachmentDao).delete(anyLong(), any(Connection.class));
        verify(phoneDao).getAllPhonesOfContact(anyLong(), any(Connection.class));
        verify(phoneDao).update(any(Phone.class), any(Connection.class));
        verify(phoneDao, times(1)).save(any(Phone.class), any(Connection.class));
        verify(phoneDao).delete(anyLong(), any(Connection.class));
        verify(photoDao).update(any(Photo.class), any(Connection.class));
    }

    @Test
    public void shouldUpdateEmptyContact() throws SQLException {
        doNothing()
                .when(contactDao).update(any(Contact.class), any(Connection.class));
        when(attachmentDao.getAllAttachmentsOfContact(anyLong(), any(Connection.class)))
                .thenReturn(emptySet());
        when(attachmentDao.save(any(Attachment.class), any(Connection.class)))
                .thenReturn(1L);
        when(phoneDao.getAllPhonesOfContact(anyLong(), any(Connection.class)))
                .thenReturn(emptySet());
        when(phoneDao.save(any(Phone.class), any(Connection.class)))
                .thenReturn(1L);
        when(contact.getPhoto())
                .thenReturn(photo);
        when(contact.getPhoto().getId())
                .thenReturn(0L);
        when(contact.getAttachments())
                .thenReturn(getAttachmentsForSave());
        when(contact.getPhones())
                .thenReturn(getPhonesForSave());

        contactService.update(contact, emptySet(), emptySet());
        verify(contactDao).update(any(Contact.class), any(Connection.class));
        verify(attachmentDao).getAllAttachmentsOfContact(anyLong(), any(Connection.class));
        verify(attachmentDao, times(2)).save(any(Attachment.class), any(Connection.class));
        verify(phoneDao).getAllPhonesOfContact(anyLong(), any(Connection.class));
        verify(phoneDao, times(2)).save(any(Phone.class), any(Connection.class));
    }

    @Test(expected = SQLException.class)
    public void shouldThrowAnExceptionWhenUpdateContact() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).update(any(Contact.class), any(Connection.class));

        contactService.update(contact, emptySet(), emptySet());
        verify(contactDao).update(any(Contact.class), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldGetContacts() throws SQLException {
        when(contactDao.getContactsList(anyLong(), anyLong(), any(Connection.class)))
                .thenReturn(emptySet());

        contactService.getContacts(0, 10);

        verify(contactDao).getContactsList(anyLong(), anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetContacts() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).getContactsList(anyLong(), anyLong(), any(Connection.class));

        contactService.getContacts(0, 10);

        verify(contactDao).getContactsList(anyLong(), anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldSearchContacts() throws SQLException {
        when(contactDao.searchContacts(any(Contact.class), any(Date.class), any(Date.class), anyLong(), anyLong(),
                any(Connection.class)))
                .thenReturn(emptySet());

        contactService.searchContacts(contact, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, 10);

        verify(contactDao).searchContacts(any(Contact.class), any(Date.class), any(Date.class), anyLong(), anyLong(),
                any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenSearchContacts() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).searchContacts(any(Contact.class), any(Date.class), any(Date.class), anyLong(), anyLong(),
                any(Connection.class));

        contactService.searchContacts(contact, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 0, 10);

        verify(contactDao).searchContacts(any(Contact.class), any(Date.class), any(Date.class), anyLong(), anyLong(),
                any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldGetSearchedContactsNumber() throws SQLException {
        when(contactDao.getNumberOfSearchContacts(any(Contact.class), any(Date.class), any(Date.class), any(Connection.class)))
                .thenReturn(1L);

        contactService.getSearchedContactsNumber(contact, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()));

        verify(contactDao).getNumberOfSearchContacts(any(Contact.class), any(Date.class), any(Date.class), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetSearchedContactsNumber() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).getNumberOfSearchContacts(any(Contact.class), any(Date.class), any(Date.class), any(Connection.class));

        contactService.getSearchedContactsNumber(contact, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()));

        verify(contactDao).getNumberOfSearchContacts(any(Contact.class), any(Date.class), any(Date.class), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldDeleteContacts() throws SQLException {
        doNothing()
                .when(contactDao).delete(anyLong(), any(Connection.class));

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        contactService.deleteContacts(ids);

        verify(contactDao, times(2)).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeleteContacts() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).delete(anyLong(), any(Connection.class));

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        contactService.deleteContacts(ids);

        verify(contactDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldGetContactsNumber() throws SQLException {
        when(contactDao.getNumberOfContacts(any(Connection.class)))
                .thenReturn(anyLong());

        contactService.getContactsNumber();

        verify(contactDao).getNumberOfContacts(any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetContactsNumber() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).getNumberOfContacts(any(Connection.class));

        contactService.getContactsNumber();

        verify(contactDao).getNumberOfContacts(any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldFindContactByEmail() throws SQLException {
        when(contactDao.findByEmail(anyString(), any(Connection.class)))
                .thenReturn(contact);

        contactService.findByEmail("email");

        verify(contactDao).findByEmail(anyString(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetContactByEmail() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).findByEmail(anyString(), any(Connection.class));

        contactService.findByEmail("email");

        verify(contactDao).findByEmail(anyString(), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test
    public void shouldFindContactsByBirthday() throws SQLException {
        when(contactDao.findContactsByBirthday(any(Date.class), any(Connection.class)))
                .thenReturn(emptySet());

        contactService.findContactsByBirthday(Date.valueOf(LocalDate.now()));

        verify(contactDao).findContactsByBirthday(any(Date.class), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenFindContactsByBirthday() throws SQLException {
        doThrow(new SQLException())
                .when(contactDao).findContactsByBirthday(any(Date.class), any(Connection.class));

        contactService.findContactsByBirthday(Date.valueOf(LocalDate.now()));

        verify(contactDao).findContactsByBirthday(any(Date.class), any(Connection.class));
        verifyNoMoreInteractions(contactDao);
    }

    private Set<Attachment> getAttachments() {
        HashSet<Attachment> attachments = new HashSet<>();
        Attachment attachment = new Attachment();
        attachment.setId(1L);
        attachments.add(attachment);
        return attachments;
    }

    private Set<Attachment> getAttachmentsForSave() {
        HashSet<Attachment> attachments = new HashSet<>();
        Attachment firstAttachment = new Attachment();
        firstAttachment.setId(1L);
        firstAttachment.setFileName("filename1");
        Attachment secondAttachment = new Attachment();
        secondAttachment.setId(2L);
        secondAttachment.setFileName("filename2");
        attachments.add(firstAttachment);
        attachments.add(secondAttachment);
        return attachments;
    }

    private Set<Phone> getPhones() {
        HashSet<Phone> phones = new HashSet<>();
        Phone phone = new Phone();
        phone.setId(1L);
        phones.add(phone);
        return phones;
    }

    private Set<Phone> getPhonesForSave() {
        HashSet<Phone> phones = new HashSet<>();
        Phone firstPhone = new Phone();
        firstPhone.setId(1L);
        firstPhone.setNumber(1);
        Phone secondPhone = new Phone();
        secondPhone.setId(2L);
        secondPhone.setNumber(2);
        phones.add(firstPhone);
        phones.add(secondPhone);
        return phones;
    }
}
