package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto.CompanyDto;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.companyEvents.CompanyCancelEvent;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.companyEvents.CompanySaveEvent;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.events.companyEvents.CompanyUpdateEvent;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.messages.CompanyValidatorsMessages;


@Tag("company-editor")
@JsModule("./views/company/company-editor.js")
public class CompanyEditor extends PolymerTemplate<CompanyEditor.CompanyEditorModel> {

    @Id("name")
    private TextField name;
    @Id("ico")
    private TextField ico;
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

    @Id("cancel")
    private Button cancel;
    @Id("save")
    private Button save;

    private final ConfirmDialog confirmDialog;

    private boolean isNew;
    private CompanyDto companyDto;
    private final BeanValidationBinder<CompanyDto> binder = new BeanValidationBinder<>(CompanyDto.class);



    public CompanyEditor() {
        isNew = false;

        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť",confirmEvent -> exit(),"Chcem ostať",cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");

        this.save.addClickListener(click -> validateAndSave());
        this.cancel.addClickListener(buttonClickEvent -> this.confirmDialog.open());


        binder.forField(name)
                .withValidator(n -> n.length() > 0, CompanyValidatorsMessages.COMPANY_NAME_NOT_BLANK_MESSAGE)
                .bind(CompanyDto::getName,CompanyDto::setName);

        binder.forField(ico)
                .withValidator(ic -> ic.length() > 0, CompanyValidatorsMessages.COMPANY_ICO_NOT_BLANK_MESSAGE)
                .withValidator(ic -> ic.matches("[0-9]{8}"),CompanyValidatorsMessages.COMPANY_ICO_FORMAT_MESSAGE)
                .bind(CompanyDto::getIco,CompanyDto::setIco);

        binder.forField(street)
                .withValidator(st -> st.length() > 0,CompanyValidatorsMessages.STREET_MESSAGE_NOT_BLANK)
                .bind(CompanyDto::getStreet,CompanyDto::setStreet);

        binder.forField(numberOfHouse)
                .withValidator(number -> number.length() > 0, CompanyValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_NOT_BLANK)
                .withValidator(number -> number.matches("[0-9]*[/]?[0-9]{1,}"),CompanyValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_FORMAT)
                .bind(CompanyDto::getNumberOfHouse,CompanyDto::setNumberOfHouse);

        binder.forField(postalCode)
                .withValidator(code -> code.length() > 0, CompanyValidatorsMessages.POSTAL_CODE_MESSAGE_NOT_BLANK)
                .withValidator(code -> code.matches("[0-9]{3}[\\s]?[0-9]{2}"),CompanyValidatorsMessages.POSTAL_CODE_MESSAGE_FORMAT)
                .bind(CompanyDto::getPostalCode,CompanyDto::setPostalCode);

        binder.forField(city)
                .withValidator(c -> c.length() > 0, CompanyValidatorsMessages.CITY_MESSAGE_NOT_BLANK)
                .withValidator(c -> c.matches("[^0-9]{1,}"),CompanyValidatorsMessages.CITY_MESSAGE_FORMAT)
                .bind(CompanyDto::getCity,CompanyDto::setCity);

        binder.forField(state)
                .withValidator(s -> s.length() > 0, CompanyValidatorsMessages.STATE_MESSAGE_NOT_BLANK)
                .withValidator(s -> s.matches("[^0-9]{1,}"),CompanyValidatorsMessages.STATE_MESSAGE_FORMAT)
                .bind(CompanyDto::getState,CompanyDto::setState);

    }

    public void setCompanyDto(CompanyDto companyDto, boolean isNew)
    {
        this.isNew = isNew;
        this.companyDto = companyDto;
        this.binder.readBean(this.companyDto);
    }

    private void validateAndSave()
    {
        try {
            binder.writeBean(this.companyDto);
            save();
        } catch (ValidationException e) {
            showErrorMessage("Nepodarilo sa uložiť spoločnosť! Skontroluj správnosť údajov.");
        }
    }

    private void save()
    {
        if(this.isNew)
            fireEvent(new CompanySaveEvent(this,this.companyDto));
        else
            fireEvent(new CompanyUpdateEvent(this,this.companyDto));

    }

    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }

    public void clear()
    {
        this.companyDto = null;
        this.isNew = false;
        this.binder.setBean(null);
        this.binder.readBean(null);
    }

    private void exit()
    {
        fireEvent(new CompanyCancelEvent(this,this.companyDto));
    }

    private void closeConfirmDialog()
    {
        if(this.confirmDialog.isOpened())
            this.confirmDialog.close();
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }




    public interface CompanyEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
