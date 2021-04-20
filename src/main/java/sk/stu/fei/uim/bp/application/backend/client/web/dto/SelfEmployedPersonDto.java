package sk.stu.fei.uim.bp.application.backend.client.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SelfEmployedPersonDto extends PersonDto
{
    @NotBlank(message = "IČO je povinné pole")
    @Pattern(regexp = "[0-9]{8}", message = "IČO musí byť 8 miestý číselný kód")
    private String ico;

    @NotBlank(message = "Obchodné meno je povinné pole")
    private String businessName;

    @NotBlank(message = "Hlavný predmet podnikania je povinné pole")
    private String businessObject;


    public SelfEmployedPersonDto(SelfEmployedPerson selfEmployedPerson)
    {
        super(selfEmployedPerson);
        setIco(selfEmployedPerson.getIco());
        setBusinessName(selfEmployedPerson.getBusinessName());
        setBusinessObject(selfEmployedPerson.getBusinessObject());
    }

    public SelfEmployedPerson toSelfEmployedPerson(SelfEmployedPerson selfEmployedPerson)
    {
        toPerson(selfEmployedPerson);
        selfEmployedPerson.setIco(getIco());
        selfEmployedPerson.setBusinessName(getBusinessName());
        selfEmployedPerson.setBusinessObject(getBusinessObject());

        return selfEmployedPerson;
    }


    @Override
    public String toString() {
        return super.toString() + "SelfEmployedPersonDto{" +
                "ico='" + ico + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businessObject='" + businessObject + '\'' +
                '}';
    }
}
