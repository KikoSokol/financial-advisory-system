package sk.stu.fei.uim.bp.application.backend.calendar.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Label;
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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.calendar.web.dto.TaskDto;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskCancelEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskDeleteEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskSaveEvent;
import sk.stu.fei.uim.bp.application.backend.calendar.web.events.taskEvents.TaskUpdateEvent;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Tag("task-editor")
@JsModule("./views/calendar/task-editor.js")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TaskEditor extends PolymerTemplate<TaskEditor.TaskEditorModel> {

    @Id("taskName")
    private TextField taskName;
//    @Id("start")
    private DateTimePicker start;
    @Id("description")
    private TextArea description;
    @Id("done")
    private Checkbox done;
    @Id("dateOfAdd")
    private Label dateOfAdd;
    @Id("save")
    private Button save;
    @Id("cancel")
    private Button cancel;
    @Id("delete")
    private Button delete;

    private final ConfirmDialog confirmDialog;
    private final ConfirmDialog deleteConfirmDialog;

    private boolean isNew;
    private TaskDto taskDto;
    private final BeanValidationBinder<TaskDto> binder = new BeanValidationBinder<>(TaskDto.class);
    @Id("dateTimePlace")
    private HorizontalLayout dateTimePlace;


    public TaskEditor()
    {

        this.start = new DateTimePicker();
        this.start.setLabel("Dátum a čas začiatku");
        this.start.setDatePlaceholder("Dátum");
        this.start.setTimePlaceholder("Čas");
        this.start.setRequiredIndicatorVisible(true);

        this.dateTimePlace.add(this.start);

        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť",confirmEvent -> exit(),"Chcem ostať",cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");

        this.deleteConfirmDialog = new ConfirmDialog("Naozaj si prajete vymazať túto úlohu?","Údaje tejto úlohy budú natrvalo odstránené !!!","Vymazať",confirmEvent -> delete(),"Zrušiť",cancelEvent -> closeDeleteConfirmDialog());
        this.deleteConfirmDialog.setConfirmButtonTheme("error primary");

        clearDateOfAdd();

        this.binder.forField(taskName)
                .withValidator(new BeanValidator(TaskDto.class,"name"))
                .bind(TaskDto::getName,TaskDto::setName);

        this.binder.forField(start)
                .withValidator(new BeanValidator(TaskDto.class,"dateTimeOfStart"))
                .bind(TaskDto::getDateTimeOfStart,TaskDto::setDateTimeOfStart);

        this.binder.forField(description)
                .bind(TaskDto::getDescription,TaskDto::setDescription);

        this.binder.forField(done)
                .bind(TaskDto::isDone,TaskDto::setDone);


        this.save.addClickListener(event -> validateAndSave());
        this.cancel.addClickListener(event -> this.confirmDialog.open());
        this.delete.addClickListener(event -> this.deleteConfirmDialog.open());



    }

    public void setTaskDto(TaskDto taskDto, boolean isNew)
    {
        this.isNew = isNew;
        this.taskDto = taskDto;
        this.binder.readBean(this.taskDto);

        this.delete.setEnabled(!isNew);

        if(!isNew)
            setDateOfAdd(this.taskDto.getDateOfAddTask());
    }

    private void validateAndSave()
    {
        try {
            this.binder.writeBean(this.taskDto);
            save();
        }catch (ValidationException e) {
            showErrorMessage("Nepodarilo sa uložiť úlohu. Skontroluj správnosť údajov!");
        }
    }

    private void save()
    {
        if(this.isNew)
        {
            this.taskDto.setDateOfAddTask(LocalDate.now());
            fireEvent(new TaskSaveEvent(this,this.taskDto));
        }
        else
            fireEvent(new TaskUpdateEvent(this,this.taskDto));
    }

    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }


    private void setDateOfAdd(LocalDate dateOfAdd)
    {
        String base = "Dátum pridania úlohy: ";

        String fullText = base.concat(dateOfAdd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        this.dateOfAdd.setText(fullText);
        this.dateOfAdd.setVisible(true);
    }

    private void clearDateOfAdd()
    {
        this.dateOfAdd.setText("");
        this.dateOfAdd.setVisible(false);
    }

    public void clear()
    {
        this.taskDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
        this.dateOfAdd.setText("");

    }

    private void exit()
    {
        fireEvent(new TaskCancelEvent(this,null));
    }

    private void delete()
    {
        if(!this.isNew)
            fireEvent(new TaskDeleteEvent(this,this.taskDto));
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

    public interface TaskEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
