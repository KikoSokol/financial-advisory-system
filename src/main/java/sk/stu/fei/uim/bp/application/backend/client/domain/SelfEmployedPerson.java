package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelfEmployedPerson extends Person
{
    @NotBlank(message = "IČO je povinné pole")
    @Pattern(regexp = "[0-9]{8}", message = "IČO musí byť 8 miestý číselný kód")
    private String ico;

    @NotBlank(message = "Obchodné meno je povinné pole")
    private String businessName;

    @NotBlank(message = "Hlavný predmet podnikania je povinné pole")
    private String businessObject;


    @Override
    public String toString() {
        return super.toString() + "SelfEmployedPerson{" +
                "\n ico=" + ico +
                "\n businessName='" + businessName + '\'' +
                "\n businessObject='" + businessObject + '\'' +
                "}\n\n";
    }
}
