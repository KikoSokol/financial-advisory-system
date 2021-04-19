package sk.stu.fei.uim.bp.application.backend.contracts.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Insurance;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.PaymentFrequency;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class InsuranceDto extends ContractDocumentDto
{
    @NotNull(message = "Poistený je povinný údaj")
    private ClientDto insured;

    @NotNull(message = "Výročie poistenia je povinný údaj")
    @Future(message = "Výročie poistenia môže byť iba v budúcnosti")
    private LocalDate anniversaryDate;

    @NotNull(message = "Platba je povinný údaj")
    @PositiveOrZero(message = "Platba môže byť len kladné číslo alebo 0")
    private double payment;

    @NotNull(message = "Frekvencia platby je povinné pole")
    private PaymentFrequency paymentFrequency;

    public InsuranceDto(Insurance insurance, ClientDto owner, ClientDto insured,ProductDto productDto)
    {
        super(insurance, owner, productDto);
        setInsured(insured);
        setAnniversaryDate(insurance.getAnniversaryDate());
        setPayment(insurance.getPayment());
        setPaymentFrequency(insurance.getPaymentFrequency());
    }

    public Insurance toInsurance(Insurance insurance)
    {
        super.toContractDocument(insurance);
        insurance.setInsured(getInsured().getClientId());
        insurance.setAnniversaryDate(getAnniversaryDate());
        insurance.setPayment(getPayment());
        insurance.setPaymentFrequency(getPaymentFrequency());

        return insurance;
    }
}
