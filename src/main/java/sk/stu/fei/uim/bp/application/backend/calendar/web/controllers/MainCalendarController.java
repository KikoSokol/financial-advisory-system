package sk.stu.fei.uim.bp.application.backend.calendar.web.controllers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.calendar.service.CalendarService;
import sk.stu.fei.uim.bp.application.backend.calendar.service.implementation.CalendarServiceImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarConverter;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarMainView;

public class MainCalendarController
{
    protected final CalendarService calendarService;
    protected final ObjectId currentAgent;
    protected final CalendarConverter calendarConverter;
    protected final CalendarMainView calendarMainView;

    public MainCalendarController(CalendarServiceImpl calendarService,
                                  ObjectId currentAgent,
                                  CalendarConverter calendarConverter,
                                  CalendarMainView calendarMainView)
    {
        this.calendarService = calendarService;
        this.currentAgent = currentAgent;
        this.calendarConverter = calendarConverter;
        this.calendarMainView = calendarMainView;
    }

}
