package sk.stu.fei.uim.bp.application.backend.calendar.web.controllers;

import com.sun.source.util.TaskEvent;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Task;
import sk.stu.fei.uim.bp.application.backend.calendar.service.implementation.CalendarServiceImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarConverter;
import sk.stu.fei.uim.bp.application.backend.calendar.web.CalendarMainView;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.TaskDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.TaskEditor;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskCancelEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskSaveEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskUpdateEvent;

import java.time.LocalDateTime;

public class TaskController extends MainCalendarController
{

    private boolean isNew;
    private Task task;
    private TaskDto taskDto;

    public TaskController(CalendarServiceImpl calendarService, ObjectId currentAgent, CalendarConverter calendarConverter, CalendarMainView calendarMainView) {
        super(calendarService, currentAgent, calendarConverter, calendarMainView);
        initActionOfEditor();
    }

    private void initActionOfEditor()
    {
        TaskEditor taskEditor = super.calendarMainView.getTaskEditor();

        taskEditor.addListener(TaskSaveEvent.class,this::doSaveNewTask);
        taskEditor.addListener(TaskUpdateEvent.class,this::doUpdateTask);
        taskEditor.addListener(TaskDeleteEvent.class,this::doDeleteTask);
        taskEditor.addListener(TaskCancelEvent.class,this::cancelEdit);
    }

    private void openEditor(TaskDto taskDto, boolean isNew)
    {
        TaskEditor taskEditor = super.calendarMainView.getTaskEditor();
        taskEditor.setTaskDto(taskDto,isNew);
        super.calendarMainView.showWindow(taskEditor);
    }


    public void addNewTask()
    {
        this.isNew = true;
        this.task = new Task();
        this.task.setAgent(super.currentAgent);
        this.taskDto = new TaskDto(this.task);
        openEditor(this.taskDto,this.isNew);
    }

    public void addNewTask(LocalDateTime startDateTime)
    {
        this.isNew = true;
        this.task = new Task();
        this.task.setAgent(super.currentAgent);
        this.taskDto = new TaskDto(this.task);
        this.taskDto.setDateTimeOfStart(startDateTime);
        openEditor(this.taskDto,this.isNew);
    }

    public void updateTask(Task task)
    {
        this.isNew = false;
        this.task = task;
        this.taskDto = new TaskDto(this.task);
        openEditor(this.taskDto,this.isNew);
    }



    private void doSaveNewTask(TaskSaveEvent event)
    {
        this.taskDto = event.getTaskDto();

        try {
            this.task = this.taskDto.toTask(this.task);
            super.calendarService.addEvent(this.task);
            successOperation("Nová úloha bola úspešne uložená");
        }
        catch (Exception exception)
        {
            super.calendarMainView.showErrorMessage("Novú úlohu sa nepodarilo uložiť. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doUpdateTask(TaskUpdateEvent event)
    {
        this.taskDto = event.getTaskDto();

        try {
            this.task = this.taskDto.toTask(this.task);
            super.calendarService.updateEvent(this.task);
            successOperation("Údaje úlohy boli úspešne zmenené");
        }
        catch (Exception exception)
        {
            super.calendarMainView.showErrorMessage("Nepodarilo sa zmeniť údaje úlohy. Skontrolujte prosím správnosť a úplnosť zadaných údajov.");
        }
    }

    private void doDeleteTask(TaskDeleteEvent event)
    {
        boolean correctDeleted = super.calendarService.deleteEvent(this.task);

        if(correctDeleted)
            successOperation("Úloha bol vymazaná");
        else
            this.calendarMainView.showErrorMessage("Údaje úlohy nebolo možné vymazať");
    }

    private void cancelEdit(TaskCancelEvent event)
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
        super.calendarMainView.getTaskEditor().clear();
        super.calendarMainView.closeWindow();
        this.isNew = false;
        this.task = null;
        this.taskDto = null;
    }







}
