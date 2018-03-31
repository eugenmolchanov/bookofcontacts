package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.entities.Attachment;
import com.itechart.javalab.firstproject.service.impl.AttachmentServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * Created by Yauhen Malchanau on 25.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceImplTest {

    @InjectMocks
    private AttachmentService attachmentService = AttachmentServiceImpl.getInstance();

    @Mock
    private AttachmentDao attachmentDao;
    @Mock
    private Attachment attachment;

    @After
    public void after() {
        reset(attachmentDao, attachment);
    }

    @Test
    public void shouldDeleteAttachment() throws SQLException {
        doNothing().when(attachmentDao).delete(anyLong(), any(Connection.class));

        attachmentService.delete(eq(anyLong()));
        verify(attachmentDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(attachmentDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeletingAttachment() throws SQLException {
        doThrow(new SQLException())
                .when(attachmentDao).delete(anyLong(), any(Connection.class));

        attachmentService.delete(1);
        verify(attachmentDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(attachmentDao);
    }

    @Test
    public void shouldFindById() throws SQLException {
        when(attachmentDao.findById(anyLong(), any(Connection.class)))
                .thenReturn(attachment);

        attachmentService.findById(eq(anyLong()));
        verify(attachmentDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(attachmentDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenFindById() throws SQLException {
        doThrow(new SQLException())
                .when(attachmentDao).findById(anyLong(), any(Connection.class));

        attachmentService.findById(eq(anyLong()));
        verify(attachmentDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(attachmentDao);
    }
}
