package com.manunin.score.service;

import com.manunin.score.model.Calendar;
import com.manunin.score.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public CalendarService(final CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public List<Calendar> getCalendars() {
        return calendarRepository.findAll();
    }

}
