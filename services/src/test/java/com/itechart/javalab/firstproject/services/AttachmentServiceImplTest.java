package com.itechart.javalab.firstproject.services;

import com.itechart.javalab.firstproject.dao.impl.AttachmentDaoImpl;
import com.itechart.javalab.firstproject.services.impl.AttachmentServiceImpl;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Евгений Молчанов on 11.09.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceImplTest {

    @Mock
    private AttachmentServiceImpl attachmentService;
    @Mock
    private AttachmentDaoImpl attachmentDao;

    @Test
    public void shouldDeleteAttachment() throws SQLException {
        doThrow(new RuntimeException()).when(attachmentDao).delete(anyLong());
        attachmentService.delete(1L);
        verify(attachmentDao).delete(anyLong());
        verifyNoMoreInteractions(attachmentDao);
    }
}
