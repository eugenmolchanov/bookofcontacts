package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.MessageDao;
import com.itechart.javalab.firstproject.entities.Message;
import com.itechart.javalab.firstproject.service.impl.MessageServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;

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
}