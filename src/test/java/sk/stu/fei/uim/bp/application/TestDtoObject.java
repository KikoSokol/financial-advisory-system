package sk.stu.fei.uim.bp.application;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;

import java.time.LocalDate;

public class TestDtoObject
{

    @Test
    public void testPhysicalPersonDto()
    {
        PhysicalPerson p = getPhysicalPerson();

        PhysicalPersonDto d = new PhysicalPersonDto(p);


        System.out.println(p);

        System.out.println(d);

        d.setFirstName("Janko");
        d.setSurname("Hrasko");

        PhysicalPerson p2 = d.toPhysicalPerson(p);

        System.out.println(p2);

        Assert.assertEquals(p,p2);
    }

    @Test
    public void testClientCompanyDto()
    {
        ClientCompany c = getClientCompany();

        ClientCompanyDto d = new ClientCompanyDto(c);

        System.out.println(c);

        System.out.println(d);

        d.setBusinessName("Velka Firma");
        d.setBusinesObject("Sport");

        ClientCompany o = d.toClientCompany(c);

        System.out.println(o);

        Assert.assertEquals(c,o);
    }






    private PhysicalPerson getPhysicalPerson()
    {
        PhysicalPerson p = new PhysicalPerson();

        Address address = new Address();
        address.setStreet("bajkalska");
        address.setNumberOfHouse("5");
        address.setPostalCode("08001");
        address.setCity("Presov");
        address.setState("Slovakia");

        p.setAddress(address);
        p.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        p.setNote("Prva testovacia fizicka osoba");
        p.setFirstName("Kristian");
        p.setSurname("Sokol");
        p.setEmail("kikosokol@gmail.com");
        p.setPhone("0948222601");
        p.setDateOfBirdth(LocalDate.now());
        p.setIban("54454545");
        p.setPersonalNumber("7737");
        p.setIdentityCardNumber("556465656565656");
        p.setCitizenship("sk");
        p.setDateOfValidityOfIdentityCard(LocalDate.now());
        p.setReleaseDateOfIdentityCard(LocalDate.now());

        return p;

    }


    private ClientCompany getClientCompany()
    {
        ClientCompany clientCompany = new ClientCompany();

        Address address = new Address();
        address.setStreet("bajkalska");
        address.setNumberOfHouse("5");
        address.setPostalCode("08001");
        address.setCity("Presov");
        address.setState("Slovakia");

        clientCompany.setAddress(address);
        clientCompany.setAgent(new ObjectId());
        clientCompany.setNote("Prvy testovaci klient");
        clientCompany.setIco("25655225");
        clientCompany.setDic("525252525");
        clientCompany.setDicDph("2525252");
        clientCompany.setBussinesObject("Produkcia");
        clientCompany.setBusinessName("Mala firma");


        return clientCompany;
    }
}
