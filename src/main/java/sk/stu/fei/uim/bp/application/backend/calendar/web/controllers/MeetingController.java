package sk.stu.fei.uim.bp.application.backend.calendar.web.controllers;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Meeting;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.MeetingAddress;
import sk.stu.fei.uim.bp.application.backend.calendar.service.implementation.CalendarServiceImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarConverter;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarMainView;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.MeetingDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.MeetingEditor;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingCancelEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingSaveEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingUpdateEvent;

import java.time.LocalDateTime;

public class MeetingController extends MainCalendarController
{

    private boolean isNew;
    private Meeting meeting;
    private MeetingDto meetingDto;

    public MeetingController(CalendarServiceImpl calendarService, ObjectId currentAgent, CalendarConverter calendarConverter, CalendarMainView calendarMainView) {
        super(calendarService, currentAgent, calendarConverter, calendarMainView);
        initActionOfEditor();
    }

    private void initActionOfEditor()
    {
        MeetingEditor meetingEditor = super.calendarMainView.getMeetingEditor();

        meetingEditor.addListener(MeetingSaveEvent.class,this::doSaveNewMeeting);
        meetingEditor.addListener(MeetingUpdateEvent.class,this::doUpdateMeeting);
        meetingEditor.addListener(MeetingDeleteEvent.class,this::doDeleteMeeting);
        meetingEditor.addListener(MeetingCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(MeetingDto meetingDto, boolean isNew)
    {
        MeetingEditor meetingEditor = super.calendarMainView.getMeetingEditor();
        meetingEditor.setMeetingDto(meetingDto,isNew);
        super.calendarMainView.showWindow(meetingEditor);
    }

    public void addNewMeeting()
    {
        this.isNew = true;
        this.meeting = new Meeting();
        this.meeting.setAgent(super.currentAgent);
        this.meeting.setAddress(new MeetingAddress());
        this.meetingDto = new MeetingDto(this.meeting,null);
        openEditor(this.meetingDto,this.isNew);
    }

    public void addNewMeeting(LocalDateTime startDateTime)
    {
        this.isNew = true;
        this.meeting = new Meeting();
        this.meeting.setAgent(super.currentAgent);
        this.meeting.setAddress(new MeetingAddress());
        this.meetingDto = new MeetingDto(this.meeting,null);
        this.meetingDto.setDateTimeOfStart(startDateTime);
        this.meetingDto.setDateTimeOfEnd(startDateTime);
        openEditor(this.meetingDto,this.isNew);
    }


    public void updateMeeting(Meeting meeting)
    {
        this.isNew = false;
        this.meeting = meeting;
        if(this.meeting.getAddress() == null)
            this.meeting.setAddress(new MeetingAddress());
        this.meetingDto = super.calendarConverter.convertMeetingToMeetingDto(meeting);
        openEditor(this.meetingDto,this.isNew);
    }

    private void doSaveNewMeeting(MeetingSaveEvent event)
    {
        this.meetingDto = event.getMeetingDto();

        try {
            this.meeting = this.meetingDto.toMeeting(this.meeting);
            super.calendarService.addEvent(this.meeting);
            successOperation("Stretnutie bolo úspešne naplánované");
        }
        catch (Exception exception)
        {
            super.calendarMainView.showErrorMessage("Nové stretnutie sa nepodarilo naplánovať. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doUpdateMeeting(MeetingUpdateEvent event)
    {
        this.meetingDto = event.getMeetingDto();

        try {
            this.meeting = this.meetingDto.toMeeting(this.meeting);
            super.calendarService.updateEvent(this.meeting);
            successOperation("Údaje stretnutia boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            this.calendarMainView.showErrorMessage("Nepodarilo sa zneniť údaje stretnutia. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doDeleteMeeting(MeetingDeleteEvent event)
    {
        boolean correctDeleted = this.calendarService.deleteEvent(this.meeting);

        if(correctDeleted)
            successOperation("Stretnutie bolo vymazané");
        else
            this.calendarMainView.showErrorMessage("Údaje stretnutia nebolo možné vymazať");
    }

    private void cancelEdit(MeetingCancelEvent event)
    {
        this.clear();
    }

    private void successOperation(String successText)
    {
        this.clear();
        this.calendarMainView.refreshCalendar();
        this.calendarMainView.showSuccessOperationNotification(successText);
    }

    public void clear()
    {
        super.calendarMainView.getMeetingEditor().clear();
        super.calendarMainView.closeWindow();
        this.isNew = false;
        this.meeting = null;
        this.meetingDto = null;
    }
}
