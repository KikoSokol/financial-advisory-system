package sk.stu.fei.uim.bp.application.backend.calendar.web;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.Timezone;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Meeting;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Task;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.MeetingDto;
import sk.stu.fei.uim.bp.application.backend.client.domain.*;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientConverter;

import java.util.LinkedList;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CalendarConverter
{
    private final ClientService clientService;
    private final ClientConverter clientConverter;

    public final static String ENTRY_EVENT_ID = "EVENT_ID";

    @Autowired
    public CalendarConverter(ClientServiceImpl clientService, ClientConverter clientConverter)
    {
        this.clientService = clientService;
        this.clientConverter = clientConverter;
    }

    public Entry convertMeetingToEntry(Meeting meeting)
    {
        Entry entry = new Entry();
        entry.setStart(meeting.getDateTimeOfStart());
        entry.setEnd(meeting.getDateTimeOfEnd());
        entry.setTitle(getClientName(meeting.getClientId()));
        entry.setDescription(meeting.getDescription());
        entry.setColor("#FF5252");
        entry.addExtendedProps(ENTRY_EVENT_ID,meeting.getEventId());

        return entry;
    }

    public Entry convertTaskToEntry(Task task)
    {
        Entry entry = new Entry();
        entry.setTitle(task.getName());
        entry.setStart(task.getDateTimeOfStart());
        entry.setDescription(task.getDescription());

        if(task.isDone())
            entry.setColor("#00E676");
        else
            entry.setColor("#2979FF");

        entry.addExtendedProps(ENTRY_EVENT_ID,task.getEventId());

        return entry;
    }


    public List<Entry> convertListOfEventToListOfEntry(List<Event> eventList)
    {
        List<Entry> entryList = new LinkedList<>();

        for(Event event : eventList)
        {
            if(event instanceof Meeting)
                entryList.add(convertMeetingToEntry((Meeting) event));
            else
                entryList.add(convertTaskToEntry((Task) event));
        }

        return entryList;
    }

    public MeetingDto convertMeetingToMeetingDto(Meeting meeting)
    {

        if(meeting.getClientId() == null)
        {
            return new MeetingDto(meeting,null);
        }

        Client client = this.clientService.getClientById(meeting.getClientId()).get();

        return new MeetingDto(meeting,this.clientConverter.convertClientToClientDto(client));
    }

    public String getClientName(ObjectId clientId)
    {
        Client client = this.clientService.getClientById(clientId).get();

        if(client instanceof Person)
        {
            Person person = (Person) client;
            return person.getSurname() + " " + person.getFirstName();
        }

        return ((ClientCompany) client).getBusinessName();
    }

}
