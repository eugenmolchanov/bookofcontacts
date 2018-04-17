package com.itechart.javalab.firstproject.service;

import com.itechart.javalab.firstproject.dao.PhoneDao;
import com.itechart.javalab.firstproject.service.impl.PhoneServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Yauhen Malchanau on 17.04.2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceImplTest {
    @InjectMocks
    private PhoneService phoneService = PhoneServiceImpl.getInstance();

    @Mock
    private PhoneDao phoneDao;

    @After
    public void after() {
        reset(phoneDao);
    }

    @Test
    public void shouldDeletePhone() throws SQLException {
        doNothing()
                .when(phoneDao).delete(anyLong(), any(Connection.class));

        phoneService.delete(1L);

        verify(phoneDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(phoneDao);
    }

    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenDeletePhone() throws SQLException {
        doThrow(new SQLException())
                .when(phoneDao).delete(anyLong(), any(Connection.class));

        phoneService.delete(1L);

        verify(phoneDao).delete(anyLong(), any(Connection.class));
        verifyNoMoreInteractions(phoneDao);
    }
}
