package sk.stu.fei.uim.bp.application.backend.calendar.web.controllers;

import org.bson.types.ObjectId;
import org.vaadin.stefan.fullcalendar.Entry;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;
import sk.stu.fei.uim.bp.application.backend.calendar.service.implementation.CalendarServiceImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarConverter;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarMainView;

import java.util.List;

public class CalendarController extends MainCalendarController
{
    public CalendarController(CalendarServiceImpl calendarService,
                              ObjectId currentAgent,
                              CalendarConverter calendarConverter,
                              CalendarMainView calendarMainView) {
        super(calendarService, currentAgent,calendarConverter,calendarMainView);
    }

    public List<Entry> getAllEvent()
    {
        List<Event> eventList = calendarService.getAllEventsByCurrentAgent(currentAgent);
        return calendarConverter.convertListOfEventToListOfEntry(eventList);
    }


}
