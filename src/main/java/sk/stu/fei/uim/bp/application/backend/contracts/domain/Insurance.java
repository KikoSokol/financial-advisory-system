package sk.stu.fei.uim.bp.application.backend.contracts.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Insurance extends ContractDocument
{
    @NotNull(message = "Poistený je povinný údaj")
    private ObjectId insured;

    @NotNull(message = "Výročie poistenia je povinný údaj")
    @Future(message = "Výročie poistenia môže byť iba v budúcnosti")
    private LocalDate anniversaryDate;

    @NotNull(message = "Platba je povinný údaj")
    @PositiveOrZero(message = "Platba môže byť len kladné číslo alebo 0")
    private double payment;

    @NotNull(message = "Frekvencia platby je povinné pole")
    private PaymentFrequency paymentFrequency;


    @Override
    public String toString() {
        return  super.toString() + "\n Insurance{" +
                "\n insured=" + insured +
                "\n anniversaryDate=" + anniversaryDate +
                "\n payment=" + payment +
                "\n paymentFrequency=" + paymentFrequency +
                '}';
    }
}
