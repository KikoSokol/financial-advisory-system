package sk.stu.fei.uim.bp.application.backend.calendar.service.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;
import sk.stu.fei.uim.bp.application.backend.calendar.repository.CalendarRepository;
import sk.stu.fei.uim.bp.application.backend.calendar.repository.implementation.CalendarRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.service.CalendarInformationService;
import sk.stu.fei.uim.bp.application.backend.calendar.service.CalendarService;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarServiceImpl implements CalendarService, CalendarInformationService
{
    private CalendarRepository calendarRepository;

    @Autowired
    public CalendarServiceImpl(CalendarRepositoryImpl calendarRepository)
    {
        this.calendarRepository = calendarRepository;
    }

    @Override
    public Event addEvent(@NotNull Event newEvent)
    {
        return this.calendarRepository.addEvent(newEvent);
    }

    @Override
    public Event updateEvent(@NotNull Event updateEvent)
    {
        return this.calendarRepository.updateEvent(updateEvent);
    }

    @Override
    public boolean deleteEvent(@NotNull Event deleteEvent)
    {
        return calendarRepository.deleteEvent(deleteEvent);
    }

    @Override
    public Optional<Event> getEventById(@NotNull ObjectId eventId)
    {
        return this.calendarRepository.getEventById(eventId);
    }

    @Override
    public List<Event> getAllEventsByCurrentAgent(@NotNull ObjectId currentAgentId)
    {
        return this.calendarRepository.getAllEventsByCurrentAgent(currentAgentId);
    }

    @Override
    public boolean isFreeThisTime(@NotNull LocalDateTime start, @NotNull LocalDateTime end)
    {
        return this.calendarRepository.isFreeThisTime(start,end);
    }

}
