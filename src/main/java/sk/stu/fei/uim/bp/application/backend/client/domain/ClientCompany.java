package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Data
public class ClientCompany extends Client
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
    private String bussinesObject;

    @NotEmpty(message = "Spoločnosť musí mať minimálne 1 konateľa")
    private List<ObjectId> managers = new LinkedList<>();


    public boolean addManger(ObjectId manager)
    {
        if(managers == null)
            managers = new LinkedList<>();

        return managers.add(manager);
    }

    public boolean removeManager(ObjectId manager)
    {
        if(managers.size() == 1)
            return false;
        return managers.remove(manager);
    }


    @Override
    public String toString() {
        return super.toString() + "ClientCompany{" +
                "\n ico=" + ico +
                "\n dic=" + dic +
                "\n dicDph='" + dicDph + '\'' +
                "\n businessName='" + businessName + '\'' +
                "\n bussinesObject='" + bussinesObject + '\'' +
                "\n managers=" + managers +
                "}\n\n";
    }


}
