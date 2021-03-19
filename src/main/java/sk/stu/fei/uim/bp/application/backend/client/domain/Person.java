package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import sk.stu.fei.uim.bp.application.validarors.anotations.PersonalNumber;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class Person extends Client
{
    @NotBlank(message = "Meno je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Meno musí obsahovať iba platné znaky")
    private String firstName;


    @NotBlank(message = "Priezvisko je ovinné pole")
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


    @NotNull
    private LocalDate dateOfValidityOfIdentityCard;


    @Past
    @NotNull
    private LocalDate releaseDateOfIdentityCard;


    private IdentifyCardCopyReference identifyCardCopy;


    @Override
    public String toString() {
        return super.toString() + "Person{" +
                "\n firstName='" + firstName + '\'' +
                "\n surname='" + surname + '\'' +
                "\n email='" + email + '\'' +
                "\n phone='" + phone + '\'' +
                "\n dateOfBirdth=" + dateOfBirdth +
                "\n iban='" + iban + '\'' +
                "\n personalNumber='" + personalNumber + '\'' +
                "\n identityCardNumber='" + identityCardNumber + '\'' +
                "\n citizenship='" + citizenship + '\'' +
                "\n identifyCardCopy=" + identifyCardCopy +
                "\n dateOfValidityOfIdentityCard=" + dateOfValidityOfIdentityCard +
                "\n releaseDateOfIdentityCard=" + releaseDateOfIdentityCard +
                "}";
    }
}
