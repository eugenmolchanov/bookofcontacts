package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.dao.impl.AttachmentDaoImpl;
import com.itechart.javalab.firstproject.services.impl.AttachmentServiceImpl;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Yauhen Malchanau on 11.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceImplTest {

    private AttachmentServiceImpl attachmentService = AttachmentServiceImpl.getInstance();
    @Mock
    private AttachmentDaoImpl attachmentDao;

    @Test
    public void shouldDeleteAttachment() throws SQLException {
        reset(attachmentDao);
        doThrow(new RuntimeException()).when(attachmentDao).delete(anyLong());
        attachmentService.delete(1L);
        verify(attachmentDao, times(1)).delete(anyLong());
        verifyNoMoreInteractions(attachmentDao);
    }
}
