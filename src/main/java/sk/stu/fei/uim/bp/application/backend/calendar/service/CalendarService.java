package sk.stu.fei.uim.bp.application.backend.calendar.service;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CalendarService
{

    Event addEvent(@NotNull Event newEvent);

    Event updateEvent(@NotNull Event updateEvent);

    boolean deleteEvent(@NotNull Event deleteEvent);

    Optional<Event> getEventById(@NotNull ObjectId eventId);

    List<Event> getAllEventsByCurrentAgent(@NotNull ObjectId currentAgentId);
}
