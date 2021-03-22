package sk.stu.fei.uim.bp.application.backend.client.web.editors;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import sk.stu.fei.uim.bp.application.backend.client.web.components.PersonalCardComponent;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonCancelEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonSaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonUpdateEvent;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.validarors.PersonalNumberValidator;
import sk.stu.fei.uim.bp.application.validarors.messages.ClientValidatorsMessages;
import java.time.LocalDate;
import java.util.Optional;

/**
 * A Designer generated component for the physical-person-editor template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("physical-person-editor")
@JsModule("./views/client/physical-person-editor.js")
public class PhysicalPersonEditor extends PolymerTemplate<PhysicalPersonEditor.PhysicalPersonEditorModel> {

    @Id("firstName")
    private TextField firstName;

    @Id("surname")
    private TextField surname;

    @Id("phone")
    private TextField phone;

    @Id("email")
    private EmailField email;

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

    @Id("iban")
    private TextField iban;

    @Id("note")
    private TextArea note;

    @Id("cancel")
    private Button cancel;

    @Id("save")
    private Button save;

    private PhysicalPersonDto physicalPersonDto;
    private final BeanValidationBinder<PhysicalPersonDto> binder = new BeanValidationBinder<>(PhysicalPersonDto.class);

    private boolean isNew;


    public PhysicalPersonEditor()
    {
        LocalDate now = LocalDate.now();
        this.isNew = true;

        save.addClickListener(click -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new PhysicalPersonCancelEvent(this,null)));


        binder.forField(firstName)
                .withValidator(name -> name.length() > 0, ClientValidatorsMessages.FIRST_NAME_MESSAGE_NOT_BLANK)
                .withValidator(name -> name.matches("[^0-9]{1,}"),ClientValidatorsMessages.FIRST_NAME_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getFirstName,PhysicalPersonDto::setFirstName);

        binder.forField(surname)
                .withValidator(surname -> surname.length() > 0, ClientValidatorsMessages.SURNAME_MESSAGE_NOT_BLANK)
                .withValidator(surname -> surname.matches("[^0-9]{1,}"),ClientValidatorsMessages.SURNAME_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getSurname,PhysicalPersonDto::setSurname);

        binder.forField(email)
                .withValidator(el -> el.length() > 0,ClientValidatorsMessages.EMAIL_MESSAGE_NOT_BLANK)
                .withValidator(new EmailValidator(ClientValidatorsMessages.EMAIL_MESSAGE_FORMAT))
                .withValidator(el -> el.matches("^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$"),ClientValidatorsMessages.EMAIL_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getEmail,PhysicalPersonDto::setEmail);

        binder.forField(phone)
                .withValidator(p -> p.length() > 0, ClientValidatorsMessages.PHONE_MESSAGE_NOT_BLANK)
                .withValidator(p -> p.matches("^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$"),ClientValidatorsMessages.PHONE_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getPhone,PhysicalPersonDto::setPhone);


        binder.forField(dateOfBirth)
                .withValidator(date -> date == null || date.isBefore(now),ClientValidatorsMessages.DATE_OF_BIRTH_MESSAGE)
                .bind(PhysicalPersonDto::getDateOfBirdth,PhysicalPersonDto::setDateOfBirdth);

        binder.forField(personalNumber)
                .withValidator(number -> number.length() > 0,ClientValidatorsMessages.PERSONAL_NUMBER_MESSAGE_NOT_BLANK)
                .withValidator(PersonalNumberValidator::isValid,ClientValidatorsMessages.PERSONAL_NUMBER_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getPersonalNumber,PhysicalPersonDto::setPersonalNumber);

        binder.forField(identityCardNumber)
                .withValidator(identityNumber -> identityNumber.length() > 0,ClientValidatorsMessages.IDENTITY_CARD_NUMBER_MESSAGE_NOT_NULL)
                .withValidator(identityNumber -> identityNumber.matches("[A-Z0-9]{8}"),ClientValidatorsMessages.IDENTITY_CARD_NUMBER_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getIdentityCardNumber,PhysicalPersonDto::setIdentityCardNumber);

        binder.forField(citizenship)
                .withValidator(ship -> ship.length() > 0,ClientValidatorsMessages.CITIZENSHIP_MESSAGE_NOT_NULL)
                .withValidator(ship -> ship.matches("[A-Z]{2,3}"),ClientValidatorsMessages.CITIZENSHIP_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getCitizenship,PhysicalPersonDto::setCitizenship);

        binder.forField(releaseDateOfIdentityCard)
                .withValidator(date -> date == null ? false : date.isBefore(now),ClientValidatorsMessages.RELEASE_DATE_OF_IDENTITY_CARD_MESSAGE_PAST)
                .bind(PhysicalPersonDto::getReleaseDateOfIdentityCard,PhysicalPersonDto::setReleaseDateOfIdentityCard);

        binder.forField(dateOfValidityOfIdentityCard)
                .withValidator(date -> date == null ? false : date.isAfter(now),ClientValidatorsMessages.DATE_OF_VALIDITY_OF_IDENTITY_CARD_MESSAGE_FUTURE)
                .bind(PhysicalPersonDto::getDateOfValidityOfIdentityCard, PhysicalPersonDto::setDateOfValidityOfIdentityCard);

        binder.forField(street)
                .withValidator(st -> st.length() > 0,ClientValidatorsMessages.STREET_MESSAGE_NOT_BLANK)
                .bind(PhysicalPersonDto::getStreet,PhysicalPersonDto::setStreet);

        binder.forField(numberOfHouse)
                .withValidator(number -> number.length() > 0,ClientValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_NOT_BLANK)
                .withValidator(number -> number.matches("[0-9]*[/]?[0-9]{1,}"),ClientValidatorsMessages.NUMBER_OF_HOUSE_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getNumberOfHouse,PhysicalPersonDto::setNumberOfHouse);

        binder.forField(postalCode)
                .withValidator(code -> code.length() > 0, ClientValidatorsMessages.POSTAL_CODE_MESSAGE_NOT_BLANK)
                .withValidator(code -> code.matches("[0-9]{3}[\\s]?[0-9]{2}"),ClientValidatorsMessages.POSTAL_CODE_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getPostalCode,PhysicalPersonDto::setPostalCode);

        binder.forField(city)
                .withValidator(c -> c.length() > 0, ClientValidatorsMessages.CITY_MESSAGE_NOT_BLANK)
                .withValidator(c -> c.matches("[^0-9]{1,}"),ClientValidatorsMessages.CITY_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getCity,PhysicalPersonDto::setCity);

        binder.forField(state)
                .withValidator(s -> s.length() > 0, ClientValidatorsMessages.STATE_MESSAGE_NOT_BLANK)
                .withValidator(s -> s.matches("[^0-9]{1,}"),ClientValidatorsMessages.STATE_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getState,PhysicalPersonDto::setState);

        binder.forField(iban)
                .withValidator(ib -> ib.length() > 0,ClientValidatorsMessages.IBAN_MESSAGE_NOT_EMPTY)
                .withValidator(ib -> ib.matches("^[A-Z]{2}[0-9]{2}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?"),ClientValidatorsMessages.IBAN_MESSAGE_FORMAT)
                .bind(PhysicalPersonDto::getIban,PhysicalPersonDto::setIban);

        binder.forField(note)
                .bind(PhysicalPersonDto::getNote,PhysicalPersonDto::setNote);


    }

    public void setPhysicalPersonDto(PhysicalPersonDto physicalPersonDto, boolean isNew)
    {
        this.isNew = isNew;
        this.physicalPersonDto = physicalPersonDto;
        this.identityCardCopy.setFiles(physicalPersonDto.getIdentifyCardCopyReference());
        this.binder.readBean(physicalPersonDto);
    }


    private void validateAndSave()
    {
        boolean isAllCorrect = false;

        boolean isCopyFilesIsCorrect = checkCopyCard();
        if(isCopyFilesIsCorrect)
        {
            setCopyFilesToThisDtoObject();
            isAllCorrect = true;
        }
        else
        {
            isAllCorrect = false;
            //TODO: dorob notifikaciu/vypisovanie ze neboli predane dva subory naraz
            System.out.println("jedna strana OP chyba");
        }

        try {
            binder.writeBean(this.physicalPersonDto);
            isAllCorrect = true;
        }
        catch (ValidationException exception)
        {
            isAllCorrect = false;
            System.out.println("Zadane údaje nie sú validné");
        }

        if(isAllCorrect)
        {
            if(isNew)
                fireEvent(new PhysicalPersonSaveEvent(this,this.physicalPersonDto));
            else
                fireEvent(new PhysicalPersonUpdateEvent(this,this.physicalPersonDto));
            System.out.println("parada");
        }
        else
        {
            //TODO:dorob info o chybach
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
            this.physicalPersonDto.setFrontSideOfPersonCard(frontSide.get());
        else
            this.physicalPersonDto.setFrontSideOfPersonCard(null);

        if(backSide.isPresent())
            this.physicalPersonDto.setBackSideOfPersonCard(backSide.get());
        else
            this.physicalPersonDto.setBackSideOfPersonCard(null);
    }




    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType,listener);
    }


    public void clear()
    {
        this.physicalPersonDto = null;
        this.binder.setBean(null);
        this.binder.readBean(null);
        this.identityCardCopy.clear();
    }






    /**
     * This model binds properties between PhysicalPersonEditor and physical-person-editor
     */
    public interface PhysicalPersonEditorModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
