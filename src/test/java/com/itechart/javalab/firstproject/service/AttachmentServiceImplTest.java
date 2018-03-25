package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.AttachmentDao;
import com.itechart.javalab.firstproject.service.impl.AttachmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

/**
 * Created by Yauhen Malchanau on 25.03.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceImplTest {

    @InjectMocks
    private AttachmentService attachmentService = AttachmentServiceImpl.getInstance();
    @Mock
    private AttachmentDao attachmentDao;

    @Before
    public void before() {
        initMocks(attachmentService);
        reset(attachmentDao);
    }

    @Test
    public void shouldDeleteAttachment() throws SQLException {
        attachmentService.delete(eq(anyLong()));
        verify(attachmentDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(attachmentDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeletingAttachment() throws SQLException {
        doThrow(new SQLException()).when(attachmentDao).delete(anyLong(), any(Connection.class));
        attachmentService.delete(1);
    }
}
