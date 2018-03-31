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
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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
                .thenReturn(getAttachments());
        when(contact.getPhones())
                .thenReturn(getPhones());
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
        verify(attachmentDao).save(any(Attachment.class), any(Connection.class));
        verify(photoDao).save(any(Photo.class), any(Connection.class));
        verify(phoneDao).save(any(Phone.class), any(Connection.class));
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

    private Set<Attachment> getAttachments() {
        HashSet<Attachment> attachments = new HashSet<>();
        attachments.add(new Attachment());
        return attachments;
    }

    private Set<Phone> getPhones() {
        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone());
        return phones;
    }
}
