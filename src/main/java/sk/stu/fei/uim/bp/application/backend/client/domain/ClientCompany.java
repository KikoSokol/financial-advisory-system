package sk.stu.fei.uim.bp.application.backend.client.domain;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

@Data
public class ClientCompany extends Client
{
    private String ico;

    private String dic;

    private String dicDph;

    private String businessName;

    private String bussinesObject;

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
