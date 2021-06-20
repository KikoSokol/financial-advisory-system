package sk.stu.fei.uim.bp.application.backend.calendar.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.calendar.service.CalendarInformationService;
import sk.stu.fei.uim.bp.application.backend.calendar.service.implementation.CalendarServiceImpl;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.MeetingDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingCancelEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingSaveEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.meetingEvents.MeetingUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientConverter;
import sk.stu.fei.uim.bp.application.backend.client.web.components.PhysicalPersonCard;
import sk.stu.fei.uim.bp.application.backend.client.web.components.SearchClientView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchClientCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.searchClientEvent.SearchGetChoosedClientEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.CalendarValidatorsMessages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;


@Tag("meeting-editor")
@JsModule("./views/calendar/meeting-editor.js")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MeetingEditor extends PolymerTemplate<MeetingEditor.MeetingEditorModel> {

//    @Id("start")
    private DateTimePicker start;
//    @Id("end")
    private DateTimePicker end;
    @Id("description")
    private TextArea description;
    @Id("addClient")
    private Button addClient;
    @Id("clientInfo")
    private PhysicalPersonCard clientInfo;
    @Id("street")
    private TextField street;
    @Id("numberOfHouse")
    private TextField numberOfHouse;
    @Id("postalCode")
    private TextField postalCode;
    @Id("city")
    private TextField city;
    @Id("state")
    private TextField state;
    @Id("save")
    private Button save;
    @Id("cancel")
    private Button cancel;
    @Id("delete")
    private Button delete;
    @Id("useClientAddress")
    private Button useClientAddress;

    private final SearchClientView searchClientView;

    private final ClientService clientService;
    private final ClientConverter clientConverter;

    private final CalendarInformationService calendarInformationService;

    private final ConfirmDialog confirmDialog;
    private final ConfirmDialog deleteConfirmDialog;

    private Dialog searchWindow;


    private boolean isNew;
    private MeetingDto meetingDto;
    private final BeanValidationBinder<MeetingDto> binder = new BeanValidationBinder<>(MeetingDto.class);
    @Id("dateTimePlace")
    private HorizontalLayout dateTimePlace;

    @Autowired
    public MeetingEditor(SearchClientView searchClientView,
                         ClientServiceImpl clientService,
                         ClientConverter clientConverter,
                         CalendarServiceImpl calendarInformationService)
    {
        this.searchClientView = searchClientView;
        this.clientService = clientService;
        this.clientConverter = clientConverter;
        this.calendarInformationService = calendarInformationService;

        this.start = new DateTimePicker();
        this.start.setLabel("Dátum a čas začiatku");
        this.start.setDatePlaceholder("Dátum");
        this.start.setTimePlaceholder("Čas");
        this.start.setRequiredIndicatorVisible(true);


        this.end = new DateTimePicker();
        this.end.setLabel("Dátum a čas konca");
        this.end.setDatePlaceholder("Dátum");
        this.end.setTimePlaceholder("Čas");
        this.end.setRequiredIndicatorVisible(true);

        this.dateTimePlace.add(start,end);

        this.isNew = false;
        this.useClientAddress.setVisible(false);
        this.clientInfo.setVisible(false);

        initSearchWindow();
        initClientSearchAction();
        initListenerToChangeEndDateTime();

        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť",confirmEvent -> exit(),"Chcem ostať",cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");

        this.deleteConfirmDialog = new ConfirmDialog("Naozaj si prajete vymazať toto naplánované stretnutie?","Údaje tohto stretnutia budú natrvalo odstránené !!!","Vymazať",confirmEvent -> delete(),"Zrušiť",cancelEvent -> closeDeleteConfirmDialog());
        this.deleteConfirmDialog.setConfirmButtonTheme("error primary");

        this.binder.forField(start)
                .withValidator(new BeanValidator(MeetingDto.class,"dateTimeOfStart"))
                .withValidator(st -> this.end.getValue() == null ? true : st.isBefore(this.end.getValue()),CalendarValidatorsMessages.START_AFTER_END)
                .bind(MeetingDto::getDateTimeOfStart,MeetingDto::setDateTimeOfStart);



        this.binder.forField(end)
                .withValidator(new BeanValidator(MeetingDto.class,"dateTimeOfEnd"))
                .withValidator(en -> this.start.getValue() == null ? true : en.isAfter(this.start.getValue()),CalendarValidatorsMessages.END_BEFORE_START)
                .bind(MeetingDto::getDateTimeOfEnd,MeetingDto::setDateTimeOfEnd);

        this.binder.forField(description)
                .bind(MeetingDto::getDescription,MeetingDto::setDescription);

        this.binder.forField(street)
                .bind(MeetingDto::getStreet,MeetingDto::setStreet);

        this.binder.forField(numberOfHouse)
//                .withValidator(new BeanValidator(MeetingDto.class,"numberOfHouse"))
                .bind(MeetingDto::getNumberOfHouse,MeetingDto::setNumberOfHouse);

        this.binder.forField(postalCode)
//                .withValidator(new BeanValidator(MeetingDto.class,"postalCode"))
                .bind(MeetingDto::getPostalCode,MeetingDto::setPostalCode);

        this.binder.forField(city)
//                .withValidator(new BeanValidator(MeetingDto.class,"city"))
                .bind(MeetingDto::getCity,MeetingDto::setCity);

        this.binder.forField(state)
//                .withValidator(new BeanValidator(MeetingDto.class,"state"))
                .bind(MeetingDto::getState,MeetingDto::setState);

        this.addClient.addClickListener(event -> showSearchWindow());
        this.useClientAddress.addClickListener(event -> setClientAddress());

        this.save.addClickListener(event -> this.validateAndSave());
        this.cancel.addClickListener(event -> this.confirmDialog.open());
        this.delete.addClickListener(event -> this.deleteConfirmDialog.open());
    }

    private void initSearchWindow()
    {
        this.searchWindow = new Dialog();
        this.searchWindow.setModal(true);
        this.searchWindow.setCloseOnOutsideClick(false);
        this.searchWindow.setWidth("70%");
        this.searchWindow.setResizable(true);
    }


    private void initClientSearchAction()
    {
        this.searchClientView.addListener(SearchGetChoosedClientEvent.class,this::setClient);
        this.searchClientView.addListener(SearchClientCancelEvent.class,this::cancelSearchClient);
        this.searchClientView.setSearchAllClient();
    }

    private void initListenerToChangeEndDateTime()
    {
        this.start.addValueChangeListener(event -> {
            if(event.getValue() != null)
                this.end.setValue(event.getValue().plusHours(1));
        });
    }

    public void setMeetingDto(MeetingDto meetingDto, boolean isNew)
    {
        this.isNew = isNew;
        this.meetingDto = meetingDto;
        this.binder.readBean(this.meetingDto);

        this.delete.setEnabled(!isNew);

        if(isNew)
        {
            this.clientInfo.setVisible(false);
            this.useClientAddress.setVisible(false);
        }
        else
        {
            if(this.meetingDto.getClientDto() != null)
                this.setInfoAboutClient(this.meetingDto.getClientDto());
        }
    }





    private void setClient(SearchGetChoosedClientEvent event)
    {
        Client client = event.getClient();
        ClientDto clientDto = this.clientConverter.convertClientToClientDto(client);

        this.meetingDto.setClientDto(clientDto);
        this.setInfoAboutClient(clientDto);
        closeSearchWindow();

    }

    private void setInfoAboutClient(ClientDto clientDto)
    {
        this.clientInfo.init(new TableClientItem(clientDto));
        this.clientInfo.setVisible(true);
        this.useClientAddress.setVisible(true);
    }

    private void cancelSearchClient(SearchClientCancelEvent event)
    {
        this.closeSearchWindow();
    }

    private void closeSearchWindow()
    {
        this.searchWindow.close();
        this.searchWindow.removeAll();
        this.searchClientView.clear();
    }

    private void showSearchWindow()
    {
        this.searchWindow.add(this.searchClientView);
        this.searchWindow.open();
    }

    private void setClientAddress()
    {
        if(this.meetingDto.getClientDto() != null)
        {
            ClientDto clientDto = this.meetingDto.getClientDto();
            this.street.setValue(clientDto.getStreet());
            this.numberOfHouse.setValue(clientDto.getNumberOfHouse());
            this.postalCode.setValue(clientDto.getPostalCode());
            this.city.setValue(clientDto.getCity());
            this.state.setValue(clientDto.getState());
        }
        else
            showErrorMessage("Adresa sa nedá nastaviť!");
    }


    private void validateAndSave()
    {
        boolean correct = true;

        if(this.meetingDto.getClientDto() == null)
        {
            correct = false;
            showErrorMessage(CalendarValidatorsMessages.MISSING_CLIENT);
        }

        if(!this.calendarInformationService.isFreeThisTime(this.start.getValue(),this.end.getValue()))
        {
            correct = false;
            showErrorMessage(CalendarValidatorsMessages.NOT_FREE_TIME_FOR_MEETING);
        }

        try
        {
            binder.writeBean(this.meetingDto);
        } catch (ValidationException e) {
            correct = false;
        }

        if(correct)
            save();
        else
            showErrorMessage("Nepodarilo sa naplánovať stretnutie. Skontroluj správnosť údajov!");
    }

    private void save()
    {
        if(isNew)
            fireEvent(new MeetingSaveEvent(this,this.meetingDto));
        else
            fireEvent(new MeetingUpdateEvent(this,this.meetingDto));
    }


    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }

    public void clear()
    {
        this.meetingDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
        this.clientInfo.setVisible(false);
        this.useClientAddress.setVisible(false);
    }

    private void exit()
    {
        fireEvent(new MeetingCancelEvent(this,null));
    }

    private void delete()
    {
        if(!this.isNew)
            fireEvent(new MeetingDeleteEvent(this,this.meetingDto));
    }

    private void closeConfirmDialog()
    {
        if(this.confirmDialog.isOpened())
        {
            this.confirmDialog.close();
        }
    }

    private void closeDeleteConfirmDialog()
    {
        if(this.deleteConfirmDialog.isOpened())
        {
            this.confirmDialog.close();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }

    public interface MeetingEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
