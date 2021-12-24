package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.services.interfaces.DateOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateOperationsServiceImplTest {
    private DateOperationsService dateOperationsService;
    @BeforeEach
    void setUp() {
        dateOperationsService = new DateOperationsServiceImpl();
    }

    @Test
    void addMonths_whenDateNotNull_returnsRightDate() {
        Date date = new Date();
        int months = 12;
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

        Date dateResult = dateOperationsService.addMonths(date, months);
        String formatResult = yyyy.format(dateResult);
        String formatCurrent = yyyy.format(date);

        assertEquals(1, Integer.parseInt(formatResult) - Integer.parseInt(formatCurrent));
    }

    @Test
    void addMonths_whenDateNull_returnsNull() {
        int months = 12;

        Date dateResult = dateOperationsService.addMonths(null, months);

        assertNull(dateResult);
    }
}