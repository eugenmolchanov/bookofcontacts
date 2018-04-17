package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.MessageDao;
import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.service.impl.MessageServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.Collections.emptySet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yauhen Malchanau on 17.04.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {
    @InjectMocks
    private MessageService messageService = MessageServiceImpl.getInstance();

    @Mock
    private MessageDao messageDao;

    @Mock
    private Message message;

    @After
    public void after() {
        reset(messageDao);
    }

    @Test
    public void shouldDeleteMessage() throws SQLException {
        doNothing()
                .when(messageDao).delete(anyLong(), any(Connection.class));

        deleteAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeleteMessage() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).delete(anyLong(), any(Connection.class));

        deleteAndVerify();
    }

    @Test
    public void shouldSave() throws SQLException {
        when(messageDao.save(any(Message.class), any(Connection.class)))
                .thenReturn(1L);

        saveAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenSaveMessage() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).save(any(Message.class), any(Connection.class));

        saveAndVerify();
    }

    @Test
    public void shouldFindMessageById() throws SQLException {
        when(messageDao.findById(anyLong(), any(Connection.class)))
                .thenReturn(message);

        findByIdAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenFindById() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).findById(anyLong(), any(Connection.class));

        findByIdAndVerify();
    }

    @Test
    public void shouldGetNotDeletedMessages() throws SQLException {
        when(messageDao.getNotDeletedMessages(anyLong(), anyLong(), any(Connection.class)))
                .thenReturn(emptySet());

        getNotDeletedMessagesAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetNotDeletedMessages() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).getNotDeletedMessages(anyLong(), anyLong(), any(Connection.class));

        getNotDeletedMessagesAndVerify();
    }

    @Test
    public void shouldGetNotDeletedMessagesNumber() throws SQLException {
        when(messageDao.getNotDeletedMessagesNumber(any(Connection.class)))
                .thenReturn(0L);

        getNotDeletedMessagesNumberAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetNotDeletedMessagesNumber() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).getNotDeletedMessagesNumber(any(Connection.class));

        getNotDeletedMessagesNumberAndVerify();
    }

    @Test
    public void shouldGetDeletedMessages() throws SQLException {
        when(messageDao.getDeletedMessages(anyLong(), anyLong(), any(Connection.class)))
                .thenReturn(emptySet());

        getDeletedMessagesAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetDeletedMessages() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).getDeletedMessages(anyLong(), anyLong(), any(Connection.class));

        getDeletedMessagesAndVerify();
    }

    @Test
    public void shouldGetDeletedMessagesNumber() throws SQLException {
        when(messageDao.getNumberOfAllDeletedMessages(any(Connection.class)))
                .thenReturn(1L);

        getDeletedMessagesNumberAndVerify();
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenGetDeletedMessagesNumber() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).getNumberOfAllDeletedMessages(any(Connection.class));

        getDeletedMessagesNumberAndVerify();
    }

    @Test
    public void shouldSendMessagesToBucket() throws SQLException {
        doNothing()
                .when(messageDao).delete(anyLong(), any(Connection.class));

        Set<Long> ids = getRandomIds();
        messageService.sendMessagesToBucket(ids);

        verify(messageDao, times(ids.size())).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenSendMessagesToBucket() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).delete(anyLong(), any(Connection.class));

        messageService.sendMessagesToBucket(getRandomIds());

        verify(messageDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    @Test
    public void shouldRemoveMessage() throws SQLException {
        doNothing()
                .when(messageDao).remove(anyLong(), any(Connection.class));

        Set<Long> ids = getRandomIds();
        messageService.remove(ids);

        verify(messageDao, times(ids.size())).remove(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenRemoveMessages() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).remove(anyLong(), any(Connection.class));

        messageService.remove(getRandomIds());

        verify(messageDao).remove(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    @Test
    public void shouldRestoreMessages() throws SQLException {
        doNothing()
                .when(messageDao).restore(anyLong(), any(Connection.class));

        Set<Long> ids = getRandomIds();
        messageService.restore(ids);

        verify(messageDao, times(ids.size())).restore(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenRestoreMessages() throws SQLException {
        doThrow(new SQLException())
                .when(messageDao).restore(anyLong(), any(Connection.class));

        messageService.restore(getRandomIds());

        verify(messageDao).restore(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void deleteAndVerify() throws SQLException {
        messageService.delete(1L);

        verify(messageDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void saveAndVerify() throws SQLException {
        messageService.save(message);

        verify(messageDao).save(any(Message.class), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void findByIdAndVerify() throws SQLException {
        messageService.findById(1L);

        verify(messageDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void getNotDeletedMessagesAndVerify() throws SQLException {
        messageService.getNotDeletedMessages(0L, 10L);

        verify(messageDao).getNotDeletedMessages(anyLong(), anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void getNotDeletedMessagesNumberAndVerify() throws SQLException {
        messageService.getNotDeletedMessagesNumber();

        verify(messageDao).getNotDeletedMessagesNumber(any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void getDeletedMessagesAndVerify() throws SQLException {
        messageService.getDeletedMessages(0L, 10L);

        verify(messageDao).getDeletedMessages(anyLong(), anyLong(), any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private void getDeletedMessagesNumberAndVerify() throws SQLException {
        messageService.getDeletedMessagesNumber();

        verify(messageDao).getNumberOfAllDeletedMessages(any(Connection.class));
        verifyNoMoreInteractions(messageDao);
    }

    private Set<Long> getRandomIds() {
        Set<Long> ids = new HashSet<>();
        ids.add(2L);
        return ids;
    }
}