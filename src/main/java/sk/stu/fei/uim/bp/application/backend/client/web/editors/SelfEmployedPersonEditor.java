package sk.stu.fei.uim.bp.application.backend.client.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.backend.client.web.components.PersonalCardComponent;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanyCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonSaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.ui.NotificationMessage;
import sk.stu.fei.uim.bp.application.ui.NotificationMessageType;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import sk.stu.fei.uim.bp.application.validarors.PersonalNumberValidator;
import sk.stu.fei.uim.bp.application.validarors.messages.ClientValidatorsMessages;
import java.time.LocalDate;
import java.util.Optional;

/**
 * A Designer generated component for the self-employed-person-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("self-employed-person-editor")
@JsModule("./views/client/self-employed-person-editor.js")
public class SelfEmployedPersonEditor extends PolymerTemplate<SelfEmployedPersonEditor.SelfEmployedPersonEditorModel> {

    @Id("firstName")
    private TextField firstName;

    @Id("surname")
    private TextField surname;

    @Id("email")
    private EmailField email;

    @Id("phone")
    private TextField phone;

    @Id("dateOfBirth")
    private DatePicker dateOfBirth;

    @Id("personalNumber")
    private TextField personalNumber;

    @Id("identityCardNumber")
    private TextField identityCardNumber;

    @Id("citizenship")
    private TextField citizenship;

    @Id("dateOfValidityOfIdentityCard")
    private DatePicker dateOfValidityOfIdentityCard;

    @Id("releaseDateOfIdentityCard")
    private DatePicker releaseDateOfIdentityCard;

    @Id("street")
    private TextField street;

    @Id("numberOfHouse")
    private TextField numberOfHouse;

    @Id("city")
    private TextField city;

    @Id("postalCode")
    private TextField postalCode;

    @Id("state")
    private TextField state;

    @Id("identityCardCopy")
    private PersonalCardComponent identityCardCopy;

    @Id("ico")
    private TextField ico;

    @Id("businessName")
    private TextField businessName;

    @Id("businessObject")
    private TextField businessObject;

    @Id("iban")
    private TextField iban;

    @Id("note")
    private TextArea note;

    @Id("cancel")
    private Button cancel;

    @Id("save")
    private Button save;

    private final ConfirmDialog confirmDialog;

    private SelfEmployedPersonDto selfEmployedPersonDto;
    private final BeanValidationBinder<SelfEmployedPersonDto> binder = new BeanValidationBinder<>(SelfEmployedPersonDto.class);

    private boolean isNew;

    public SelfEmployedPersonEditor()
    {
        this.confirmDialog = new ConfirmDialog("Naozaj si prajete zavrieť editor?","Všetky zmeny nebudú uložené !!!","Naozaj chcem odísť", confirmEvent -> exit(),"Chcem ostať", cancelEvent -> closeConfirmDialog());
        this.confirmDialog.setConfirmButtonTheme("error primary");

        LocalDate now = LocalDate.now();
        this.isNew = true;

        save.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> this.confirmDialog.open());

        binder.forField(firstName)
                .withValidator(name -> name.length() > 0, ClientValidatorsMessages.FIRST_NAME_MESSAGE_NOT_BLANK)
                .withValidator(name -> name.matches("[^0-9]{1,}"),ClientValidatorsMessages.FIRST_NAME_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getFirstName,SelfEmployedPersonDto::setFirstName);

        binder.forField(surname)
                .withValidator(surname -> surname.length() > 0, ClientValidatorsMessages.SURNAME_MESSAGE_NOT_BLANK)
                .withValidator(surname -> surname.matches("[^0-9]{1,}"),ClientValidatorsMessages.SURNAME_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getSurname,SelfEmployedPersonDto::setSurname);

        binder.forField(email)
                .withValidator(el -> el.length() > 0,ClientValidatorsMessages.EMAIL_MESSAGE_NOT_BLANK)
                .withValidator(new EmailValidator(ClientValidatorsMessages.EMAIL_MESSAGE_FORMAT))
                .withValidator(el -> el.matches("^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$"),ClientValidatorsMessages.EMAIL_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getEmail,SelfEmployedPersonDto::setEmail);

        binder.forField(phone)
                .withValidator(p -> p.length() > 0, ClientValidatorsMessages.PHONE_MESSAGE_NOT_BLANK)
                .withValidator(p -> p.matches("^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$"),ClientValidatorsMessages.PHONE_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getPhone,SelfEmployedPersonDto::setPhone);


        binder.forField(dateOfBirth)
                .withValidator(date -> date == null || date.isBefore(now),ClientValidatorsMessages.DATE_OF_BIRTH_MESSAGE)
                .bind(SelfEmployedPersonDto::getDateOfBirdth,SelfEmployedPersonDto::setDateOfBirdth);

        binder.forField(personalNumber)
                .withValidator(number -> number.length() > 0,ClientValidatorsMessages.PERSONAL_NUMBER_MESSAGE_NOT_BLANK)
                .withValidator(PersonalNumberValidator::isValid,ClientValidatorsMessages.PERSONAL_NUMBER_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getPersonalNumber,SelfEmployedPersonDto::setPersonalNumber);

        binder.forField(identityCardNumber)
                .withValidator(identityNumber -> identityNumber.length() > 0,ClientValidatorsMessages.IDENTITY_CARD_NUMBER_MESSAGE_NOT_NULL)
                .withValidator(identityNumber -> identityNumber.matches("[A-Z0-9]{8}"),ClientValidatorsMessages.IDENTITY_CARD_NUMBER_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getIdentityCardNumber,SelfEmployedPersonDto::setIdentityCardNumber);

        binder.forField(citizenship)
                .withValidator(ship -> ship.length() > 0,ClientValidatorsMessages.CITIZENSHIP_MESSAGE_NOT_NULL)
                .withValidator(ship -> ship.matches("[A-Z]{2,3}"),ClientValidatorsMessages.CITIZENSHIP_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getCitizenship,SelfEmployedPersonDto::setCitizenship);

        binder.forField(releaseDateOfIdentityCard)
                .withValidator(date -> date == null ? false : date.isBefore(now),ClientValidatorsMessages.RELEASE_DATE_OF_IDENTITY_CARD_MESSAGE_PAST)
                .bind(SelfEmployedPersonDto::getReleaseDateOfIdentityCard,SelfEmployedPersonDto::setReleaseDateOfIdentityCard);

        binder.forField(dateOfValidityOfIdentityCard)
                .withValidator(date -> date == null ? false : date.isAfter(now),ClientValidatorsMessages.DATE_OF_VALIDITY_OF_IDENTITY_CARD_MESSAGE_FUTURE)
                .bind(SelfEmployedPersonDto::getDateOfValidityOfIdentityCard, SelfEmployedPersonDto::setDateOfValidityOfIdentityCard);

        binder.forField(street)
                .withValidator(st -> st.length() > 0,ClientValidatorsMessages.STREET_MESSAGE_NOT_BLANK)
                .bind(SelfEmployedPersonDto::getStreet,SelfEmployedPersonDto::setStreet);

        binder.forField(numberOfHouse)
                .withValidator(number -> number.length() > 0, ClientValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_NOT_BLANK)
                .withValidator(number -> number.matches("[0-9]*[/]?[0-9]{1,}"),ClientValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getNumberOfHouse,SelfEmployedPersonDto::setNumberOfHouse);

        binder.forField(postalCode)
                .withValidator(code -> code.length() > 0, ClientValidatorsMessages.POSTAL_CODE_MESSAGE_NOT_BLANK)
                .withValidator(code -> code.matches("[0-9]{3}[\\s]?[0-9]{2}"),ClientValidatorsMessages.POSTAL_CODE_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getPostalCode,SelfEmployedPersonDto::setPostalCode);

        binder.forField(city)
                .withValidator(c -> c.length() > 0, ClientValidatorsMessages.CITY_MESSAGE_NOT_BLANK)
                .withValidator(c -> c.matches("[^0-9]{1,}"),ClientValidatorsMessages.CITY_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getCity,SelfEmployedPersonDto::setCity);

        binder.forField(state)
                .withValidator(s -> s.length() > 0, ClientValidatorsMessages.STATE_MESSAGE_NOT_BLANK)
                .withValidator(s -> s.matches("[^0-9]{1,}"),ClientValidatorsMessages.STATE_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getState,SelfEmployedPersonDto::setState);

        binder.forField(iban)
                .withValidator(ib -> ib.length() > 0,ClientValidatorsMessages.IBAN_MESSAGE_NOT_EMPTY)
                .withValidator(ib -> ib.matches("^[A-Z]{2}[0-9]{2}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?"),ClientValidatorsMessages.IBAN_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getIban,SelfEmployedPersonDto::setIban);

        binder.forField(note)
                .bind(SelfEmployedPersonDto::getNote,SelfEmployedPersonDto::setNote);

        binder.forField(ico)
                .withValidator(ic -> ic.length() > 0,ClientValidatorsMessages.ICO_MESSAGE_NOT_BLANK)
                .withValidator(ic -> ic.matches("[0-9]{8}"),ClientValidatorsMessages.ICO_MESSAGE_FORMAT)
                .bind(SelfEmployedPersonDto::getIco,SelfEmployedPersonDto::setIco);

        binder.forField(businessName)
                .withValidator(name -> name.length() > 0, ClientValidatorsMessages.BUSINESS_NAME_MESSAGE_NOT_BLANK)
                .bind(SelfEmployedPersonDto::getBusinessName,SelfEmployedPersonDto::setBusinessName);

        binder.forField(businessObject)
                .withValidator(object -> object.length() > 0,ClientValidatorsMessages.BUSINESS_OBJECT_MESSAGE_NOT_BLANK)
                .bind(SelfEmployedPersonDto::getBusinessObject,SelfEmployedPersonDto::setBusinessObject);

    }

    public void setSelfEmployedPersonDto(SelfEmployedPersonDto selfEmployedPersonDto, boolean isNew)
    {
        this.isNew = isNew;
        this.selfEmployedPersonDto = selfEmployedPersonDto;
        this.identityCardCopy.setFiles(selfEmployedPersonDto.getIdentifyCardCopyReference());
        this.binder.readBean(selfEmployedPersonDto);
    }

    private void validateAndSave()
    {
        boolean isAllCorrect = true;

        boolean isCopyFilesIsCorrect = checkCopyCard();
        if(isCopyFilesIsCorrect)
        {
            setCopyFilesToThisDtoObject();
        }
        else
        {
            isAllCorrect = false;
            showErrorMessage(ClientValidatorsMessages.MISSING_ONE_SIDE_OF_PERSONAL_CARD);
        }

        try {
            binder.writeBean(this.selfEmployedPersonDto);
        }
        catch(ValidationException exception)
        {
            isAllCorrect = false;
        }

        if(isAllCorrect)
        {
            if(isNew)
                fireEvent(new SelfEmployedPersonSaveEvent(this,this.selfEmployedPersonDto));
            else
                fireEvent(new SelfEmployedPersonUpdateEvent(this,this.selfEmployedPersonDto));
        }
        else
        {
            System.out.println("Neocakavana chyba");
        }




    }


    private boolean checkCopyCard()
    {
        Optional<FileWrapper> frontSideCopyCard = this.identityCardCopy.getFrontSideFile();
        Optional<FileWrapper> backSideCopyCard = this.identityCardCopy.getBackSideFile();

        return (frontSideCopyCard.isPresent() && backSideCopyCard.isPresent()) || (frontSideCopyCard.isEmpty() && backSideCopyCard.isEmpty());
    }

    private void setCopyFilesToThisDtoObject()
    {
        Optional<FileWrapper> frontSide = this.identityCardCopy.getFrontSideFile();
        Optional<FileWrapper> backSide = this.identityCardCopy.getBackSideFile();

        if(frontSide.isPresent())
            this.selfEmployedPersonDto.setFrontSideOfPersonCard(frontSide.get());
        else
            this.selfEmployedPersonDto.setFrontSideOfPersonCard(null);

        if(backSide.isPresent())
            this.selfEmployedPersonDto.setBackSideOfPersonCard(backSide.get());
        else
            this.selfEmployedPersonDto.setBackSideOfPersonCard(null);
    }

    public void clear()
    {
        this.selfEmployedPersonDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
        this.identityCardCopy.clear();
    }


    private void exit()
    {
        fireEvent(new SelfEmployedPersonCancelEvent(this,null));
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

    private void showErrorMessage(String errorText)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorText);
    }



    /**
     * This model binds properties between SelfEmployedPersonEditor and self-employed-person-editor
     */
    public interface SelfEmployedPersonEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
