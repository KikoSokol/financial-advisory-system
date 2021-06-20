package sk.stu.fei.uim.bp.application.backend.calendar.web;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.*;
import sk.stu.fei.uim.bp.application.MainView;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Event;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Meeting;
import sk.stu.fei.uim.bp.application.backend.calendar.domain.Task;
import sk.stu.fei.uim.bp.application.backend.calendar.service.CalendarService;
import sk.stu.fei.uim.bp.application.backend.calendar.service.implementation.CalendarServiceImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.web.controllers.CalendarController;
import sk.stu.fei.uim.bp.application.backend.calendar.web.controllers.MeetingController;
import sk.stu.fei.uim.bp.application.backend.calendar.web.controllers.TaskController;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.MeetingEditor;
import sk.stu.fei.uim.bp.application.backend.calendar.web.editors.TaskEditor;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;

import java.time.*;
import java.util.Locale;
import java.util.Optional;


@Tag("calendar-main-view")
@JsModule("./views/calendar/calendar-main-view.js")
@PageTitle(value = "Kalendár")
@Route(value = "calendar", layout = MainView.class)
public class CalendarMainView extends PolymerTemplate<CalendarMainView.CalendarMainViewModel> {

    @Id("past")
    private Button past;
    @Id("chooseMonth")
    private DatePicker chooseMonth;
    @Id("future")
    private Button future;
    @Id("actualMonth")
    private Label actualMonth;
    @Id("calendarPlace")
    private HorizontalLayout calendarPlace;
    @Id("addMeeting")
    private Button addMeeting;
    @Id("addTask")
    private Button addTask;

    private HorizontalLayout addEventButtonsPlace;
    private Button addMeetingInEventButtonsPlace;
    private Button addTaskInEventButtonsPlace;
    private Dialog addButtonsDialog;



    private final FullCalendar calendar;

    private Dialog mainWindow;

    private final ObjectId currentAgentId = new ObjectId("601b6300dbf3207494372a20");


//    private final CalendarConverter calendarConverter;

    private final CalendarController calendarController;
    private final MeetingController meetingController;
    private final TaskController taskController;

    private final MeetingEditor meetingEditor;
    private final TaskEditor taskEditor;

    private final CalendarService calendarService;



    @Autowired
    public CalendarMainView(CalendarServiceImpl calendarService,
                            CalendarConverter calendarConverter,
                            MeetingEditor meetingEditor,
                            TaskEditor taskEditor)
    {
//        this.calendarConverter = calendarConverter;
        this.calendarService = calendarService;

        this.meetingEditor = meetingEditor;
        this.taskEditor = taskEditor;

        this.calendarController = new CalendarController(calendarService,currentAgentId,calendarConverter,this);
        this.meetingController = new MeetingController(calendarService,currentAgentId,calendarConverter,this);
        this.taskController = new TaskController(calendarService,currentAgentId,calendarConverter,this);

        initMainWindow();

        calendar = FullCalendarBuilder.create().build();
        this.calendarPlace.add(calendar);
        setCalendarParameter();
        setActionOnControlButton();
        setActionOnChooseMonth();

        refreshCalendar();

        this.addMeeting.addClickListener(event -> {
            this.meetingController.addNewMeeting();
        });

        this.addTask.addClickListener(event -> {
            this.taskController.addNewTask();
        });


        setListenerOnCalendar();

        initChooseEvent();

    }


    private void setCalendarParameter()
    {
        this.calendar.setFirstDay(DayOfWeek.MONDAY);
        this.calendar.setBusinessHours(new BusinessHours(LocalTime.of(6, 0), LocalTime.of(17, 0),BusinessHours.DEFAULT_BUSINESS_WEEK));
//        this.calendar.setTimezone(new Timezone(ZoneId.of("Europe/Bratislava"),"Europe/Bratislava"));
        this.calendar.setLocale(Locale.forLanguageTag("SK"));

        LocalDate now = LocalDate.now();
        this.calendar.gotoDate(now);
        this.chooseMonth.setValue(now);
        this.actualMonth.setText(getMonthName(now.getMonthValue(), now.getYear()));

    }

    private void setListenerOnCalendar()
    {
        this.calendar.addEntryClickedListener(event -> {
            Entry entry = event.getEntry();
            updateEvent((ObjectId) entry.getExtendedProps().get(CalendarConverter.ENTRY_EVENT_ID));
        });

        this.calendar.addTimeslotClickedListener(event -> {
            openChooseButtonsForAddEvent(event.getDateTime());
        });

        this.calendar.addDayNumberClickedListener(event -> {
            LocalDate eventDate = event.getDate();
            LocalDateTime date = LocalDateTime.of(eventDate.getYear(),eventDate.getMonthValue(),eventDate.getDayOfMonth(),0,0);
            openChooseButtonsForAddEvent(date);
        });
    }

    private void setActionOnControlButton()
    {
        this.past.addClickListener(event -> {
            LocalDate date = this.chooseMonth.getValue().minusMonths(1);
            this.chooseMonth.setValue(date);
            this.calendar.gotoDate(date);
            this.actualMonth.setText(getMonthName(date.getMonthValue(), date.getYear()));
        });

        this.future.addClickListener(event -> {
            LocalDate date = this.chooseMonth.getValue().plusMonths(1);
            this.chooseMonth.setValue(date);
            this.calendar.gotoDate(date);
            this.actualMonth.setText(getMonthName(date.getMonthValue(), date.getYear()));
        });
    }

    private void setActionOnChooseMonth()
    {
        this.chooseMonth.addValueChangeListener(event -> {
            this.calendar.gotoDate(event.getValue());
            this.actualMonth.setText(getMonthName(event.getValue().getMonthValue(),event.getValue().getYear()));
        });
    }

    private void initMainWindow()
    {
        this.mainWindow = new Dialog();
        this.mainWindow.setModal(true);
        this.mainWindow.setCloseOnOutsideClick(false);
    }

    public void showWindow(Component component)
    {
        this.mainWindow.add(component);
        this.mainWindow.open();
    }

    public void closeWindow()
    {
        this.mainWindow.removeAll();
        this.mainWindow.close();
    }

    private String getMonthName(int month, int year)
    {
        String name = "";

        if(month == 1)
        {
            name = name.concat("Január ");
        }
        else if(month == 2)
            name = name.concat("Február ");
        else if(month == 3)
            name = name.concat("Marec ");
        else if(month == 4)
            name = name.concat("Apríl ");
        else if(month == 5)
            name = name.concat("Máj ");
        else if(month == 6)
            name = name.concat("Jún ");
        else if(month == 7)
            name = name.concat("Júl ");
        else if(month == 8)
            name = name.concat("August ");
        else if(month == 9)
            name = name.concat("September ");
        else if(month == 10)
            name = name.concat("Október ");
        else if(month == 11)
            name = name.concat("November ");
        else if(month == 12)
            name = name.concat("December ");

        name = name.concat(Integer.toString(year));

        return name;
    }


    public void refreshCalendar()
    {
        this.calendar.removeAllEntries();
        this.calendar.addEntries(this.calendarController.getAllEvent());
    }


    private void updateEvent(ObjectId eventId)
    {
        Optional<Event> eventOptional = this.calendarService.getEventById(eventId);

        if(eventOptional.isEmpty())
        {
            showErrorMessage("Úlohu alebo stretnutie sa nepodarilo načítať");
            return;
        }

        Event event = eventOptional.get();

        if(event instanceof Meeting)
        {
            this.meetingController.updateMeeting((Meeting) event);
        }
        else
            this.taskController.updateTask((Task) event);
    }




    private void initChooseEvent()
    {
        this.addEventButtonsPlace = new HorizontalLayout();
        this.addButtonsDialog = new Dialog();

        this.addMeetingInEventButtonsPlace = new Button();
        this.addMeetingInEventButtonsPlace.setText("Stretnutie");

        this.addTaskInEventButtonsPlace = new Button();
        this.addTaskInEventButtonsPlace.setText("Úloha");

        this.addEventButtonsPlace.add(this.addMeetingInEventButtonsPlace,this.addTaskInEventButtonsPlace);

        this.addButtonsDialog.add(this.addEventButtonsPlace);

        this.addButtonsDialog.setCloseOnOutsideClick(true);
    }

    private void openChooseButtonsForAddEvent(LocalDateTime dateTime)
    {
        this.addMeetingInEventButtonsPlace.addClickListener(event -> {
            this.meetingController.addNewMeeting(dateTime);
            closeAddButtonsDialog();
        });

        this.addTaskInEventButtonsPlace.addClickListener(event -> {
            this.taskController.addNewTask(dateTime);
            closeAddButtonsDialog();
        });

        this.addButtonsDialog.open();

    }


    private void closeAddButtonsDialog()
    {
        this.addButtonsDialog.close();
    }




    public MeetingEditor getMeetingEditor() {
        return meetingEditor;
    }

    public TaskEditor getTaskEditor() {
        return taskEditor;
    }


    public void showSuccessOperationNotification(String successMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showSuccessMessage(successMessage,5000);
    }

    public void showErrorMessage(String errorMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorMessage);
    }

    public interface CalendarMainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
