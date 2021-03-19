package sk.stu.fei.uim.bp.application.backend.client.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientCompanyDto extends ClientDto
{
    @NotBlank(message = "IČO je povinné pole")
    @Pattern(regexp = "[0-9]{8}", message = "IČO musí byť 8 miestý číselný kód")
    private String ico;

    @NotBlank(message = "DIČ je povinné pole")
    @Pattern(regexp = "[0-9]{10}", message = "DIČ musí byť 10 miestný číselný kód")
    private String dic;

    @NotBlank(message = "DIČ DPH je povinné pole")
    @Size(max = 12, min = 12, message = "DIČ DPH je 12 miestný kód")
    @Pattern(regexp = "^SK[0-9]{10}", message = "DIČ DPH musí byť v tvare SKxxxxxxxxxx (x je číslo)")
    private String dicDph;

    @NotBlank(message = "Obchodné meno je povinné pole")
    private String businessName;

    @NotBlank(message = "Hlavný predmet podnikania je povinné pole")
    private String businesObject;

    @NotEmpty(message = "Spoločnosť musí mať minimálne 1 konateľa")
    private List<ObjectId> managers = new LinkedList<>();


    public ClientCompanyDto(ClientCompany company)
    {
        super(company);
        setIco(company.getIco());
        setDic(company.getDic());
        setDicDph(company.getDicDph());
        setBusinessName(company.getBusinessName());
        setBusinesObject(company.getBussinesObject());
        setManagers(company.getManagers());
    }


    public ClientCompany toClientCompany(ClientCompany company)
    {
        toClient(company);
        company.setIco(getIco());
        company.setDic(getDic());
        company.setDicDph(getDicDph());
        company.setBusinessName(getBusinessName());
        company.setBussinesObject(getBusinesObject());
        company.setManagers(getManagers());

        return company;
    }


    @Override
    public String toString() {
        return super.toString() + "ClientCompanyDto{" +
                "ico='" + ico + '\'' +
                ", dic='" + dic + '\'' +
                ", dicDph='" + dicDph + '\'' +
                ", businessName='" + businessName + '\'' +
                ", businesObject='" + businesObject + '\'' +
                ", managers=" + managers +
                '}';
    }
}
