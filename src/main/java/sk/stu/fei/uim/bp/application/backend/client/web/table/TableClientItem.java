package sk.stu.fei.uim.bp.application.backend.client.web.table;

import lombok.Data;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.*;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.*;

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

    public TableClientItem(ClientDto client)
    {
        if(client instanceof ClientCompanyDto)
            initCompany((ClientCompanyDto) client);
        if(client instanceof PhysicalPersonDto || client instanceof SelfEmployedPersonDto)
            initPerson((PersonDto) client);

    }

    public TableClientItem(ObjectId id)
    {
        setId(id);
        setName("");
        setSurname("");
        setEmail("");
        setPhone("");
        setPersonalNumber("");
        setIco("");
    }

    private void initCompany(ClientCompanyDto clientCompany)
    {
        setId(clientCompany.getClientId());
        setName(clientCompany.getBusinessName());
        setSurname("");
        setEmail(clientCompany.getManagers().get(0).getEmail());
        setPhone(clientCompany.getManagers().get(0).getPhone());
        setPersonalNumber("");
        setIco(clientCompany.getIco());
    }

    private void initPerson(PersonDto person)
    {
        setId(person.getClientId());
        setName(person.getFirstName());
        setSurname(person.getSurname());
        setEmail(person.getEmail());
        setPhone(person.getPhone());
        setPersonalNumber(person.getPersonalNumber());

        if(person instanceof SelfEmployedPersonDto)
        {
            SelfEmployedPersonDto selfEmployedPersonDto = (SelfEmployedPersonDto) person;
            setIco(selfEmployedPersonDto.getIco());
        }
        else
            setIco("");
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }
}
