package com.yakymets.creditsystem.services.implementations;

import com.yakymets.creditsystem.services.interfaces.DateOperationsService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class DateOperationsServiceImpl implements DateOperationsService {
    @Override
    public Date addMonths(Date today, int monthsToAdd) {
        if (today != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.MONTH, monthsToAdd);
            return c.getTime();
        } else {
            return null;
        }
    }
}
