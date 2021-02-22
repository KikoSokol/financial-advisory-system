package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class Person extends Client
{
    private String firstName;

    private String surname;

    private String email;

    private String phone;

    private LocalDate dateOfBirdth;

    private String iban;

    private String personalNumber;

    private String identityCardNumber;

    private String citizenship;

    private LocalDate dateOfValidityOfIdentityCard;

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
