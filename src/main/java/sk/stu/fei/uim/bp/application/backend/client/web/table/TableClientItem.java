package sk.stu.fei.uim.bp.application.backend.client.web.table;

import lombok.Data;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.*;

@Data
public class TableClientItem
{
    private ObjectId id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String personalNumber;
    private String ico;

    public TableClientItem(Client client)
    {
        if(client instanceof ClientCompany)
            initCompany((ClientCompany) client);
        if(client instanceof PhysicalPerson || client instanceof SelfEmployedPerson)
            initPerson((Person) client);

    }

    private void initCompany(ClientCompany clientCompany)
    {
        setId(clientCompany.getClientId());
        setName(clientCompany.getBusinessName());
        setSurname("");
        setEmail("");
        setPhone("");
        setPersonalNumber("");
        setIco(clientCompany.getIco());
    }

    private void initPerson(Person person)
    {
        setId(person.getClientId());
        setName(person.getFirstName());
        setSurname(person.getSurname());
        setEmail(person.getEmail());
        setPhone(person.getPhone());
        setPersonalNumber(person.getPersonalNumber());
        setIco("");
    }
}
