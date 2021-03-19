package sk.stu.fei.uim.bp.application.backend.client.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import sk.stu.fei.uim.bp.application.backend.client.domain.IdentifyCardCopyReference;
import sk.stu.fei.uim.bp.application.backend.client.domain.Person;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.validarors.anotations.PersonalNumber;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class PersonDto extends ClientDto
{
    @NotBlank(message = "Meno je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Meno musí obsahovať iba platné znaky")
    private String firstName;


    @NotBlank(message = "Priezvisko je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Priezvisko musí obsahovať iba platné znaky")
    private String surname;


    @NotBlank(message = "Email je povinné pole")
    @Email(regexp = "^([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}$",message = "Email musí mať správny tvar")
    private String email;


    @NotBlank(message = "Telefón je povinné pole")
    @Pattern(regexp = "^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$",message = "Telefónné číslo musí mať správny tvar")
    private String phone;


    @Past(message = "Dátum narodenia musí byť v minulosti")
    private LocalDate dateOfBirdth;


    @NotBlank(message = "Iban je povinné pole")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?[0-9]{4}[\\s]?", message = "Iban musí mať správny tvar")
    private String iban;


    @NotBlank(message = "Rodné číslo je povinné pole")
    @PersonalNumber()
    private String personalNumber;


    @NotBlank(message = "Číslo občianského preukazu je povinné pole")
    @Pattern(regexp = "[A-Z0-9]{8}", message = "Číslo občianskeho preukazu musí mať správny tvar")
    private String identityCardNumber;


    @NotBlank(message = "Štátne občianstvo je povinné pole")
    @Pattern(regexp = "[A-Z]{2,3}",message = "Štátne občianstvo musí mať 2 alebo 3 veľké alfabetické znaky")
    private String citizenship;

    @Future
    private LocalDate dateOfValidityOfIdentityCard;


    @Past
    private LocalDate releaseDateOfIdentityCard;

    private FileWrapper frontSideOfPersonCard;

    private FileWrapper backSideOfPersonCard;

    private IdentifyCardCopyReference identifyCardCopyReference;

    protected PersonDto(Person person)
    {
        super(person);
        setFirstName(person.getFirstName());
        setSurname(person.getSurname());
        setEmail(person.getEmail());
        setPhone(person.getPhone());
        setDateOfBirdth(person.getDateOfBirdth());
        setIban(person.getIban());
        setPersonalNumber(person.getPersonalNumber());
        setIdentityCardNumber(person.getIdentityCardNumber());
        setCitizenship(person.getCitizenship());
        setDateOfValidityOfIdentityCard(person.getDateOfValidityOfIdentityCard());
        setReleaseDateOfIdentityCard(person.getReleaseDateOfIdentityCard());
        setIdentifyCardCopyReference(person.getIdentifyCardCopy());
    }


    protected Person toPerson(Person person)
    {
        toClient(person);
        person.setFirstName(getFirstName());
        person.setSurname(getSurname());
        person.setEmail(getEmail());
        person.setPhone(getPhone());
        person.setDateOfBirdth(getDateOfBirdth());
        person.setIban(getIban());
        person.setPersonalNumber(getPersonalNumber());
        person.setIdentityCardNumber(getIdentityCardNumber());
        person.setCitizenship(getCitizenship());
        person.setDateOfValidityOfIdentityCard(getDateOfValidityOfIdentityCard());
        person.setReleaseDateOfIdentityCard(getReleaseDateOfIdentityCard());

        return person;
    }

    @Override
    public String toString() {
        return super.toString() + "PersonDto{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirdth=" + dateOfBirdth +
                ", iban='" + iban + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", identityCardNumber='" + identityCardNumber + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", dateOfValidityOfIdentityCard=" + dateOfValidityOfIdentityCard +
                ", releaseDateOfIdentityCard=" + releaseDateOfIdentityCard +
                '}';
    }
}
