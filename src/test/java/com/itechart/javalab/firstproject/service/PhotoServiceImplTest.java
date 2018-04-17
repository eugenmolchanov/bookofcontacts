package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.PhotoDao;
import com.itechart.javalab.firstproject.entities.Photo;
import com.itechart.javalab.firstproject.service.impl.PhotoServiceImpl;
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
public class PhotoServiceImplTest {
    @InjectMocks
    private PhotoService photoService = PhotoServiceImpl.getInstance();

    @Mock
    private PhotoDao photoDao;

    @Mock
    private Photo photo;

    @After
    public void after() {
        reset(photoDao);
    }

    @Test
    public void shouldDeletePhoto() throws SQLException {
        doNothing()
                .when(photoDao).delete(anyLong(), any(Connection.class));

        photoService.delete(1L);

        verify(photoDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(photoDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeletePhoto() throws SQLException {
        doThrow(new SQLException())
                .when(photoDao).delete(anyLong(), any(Connection.class));

        photoService.delete(1L);

        verify(photoDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(photoDao);
    }

    @Test
    public void shouldFindPhotoById() throws SQLException {
        when(photoDao.findById(anyLong(), any(Connection.class)))
                .thenReturn(photo);

        photoService.findById(1L);

        verify(photoDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(photoDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenFindPhotoById() throws SQLException {
        doThrow(new SQLException())
                .when(photoDao).findById(anyLong(), any(Connection.class));

        photoService.findById(1L);

        verify(photoDao).findById(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(photoDao);
    }
}
