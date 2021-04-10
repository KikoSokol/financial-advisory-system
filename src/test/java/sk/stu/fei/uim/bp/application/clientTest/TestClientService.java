package sk.stu.fei.uim.bp.application.clientTest;


import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestClientService
{

    @Autowired
    ClientServiceImpl clientService;

    @Test
    public void test()
    {
        PhysicalPerson physicalPerson = getPhysicalPerson();

        System.out.println("FO nova pred vlozeniem");
        System.out.println(physicalPerson);

        PhysicalPerson fromDbFO = this.clientService.addNewPhysicalPerson(physicalPerson);

        assert fromDbFO != null;



        System.out.println("FO nava z databazy");
        System.out.println(fromDbFO);

        physicalPerson.setClientId(fromDbFO.getClientId());

        Assert.assertEquals(physicalPerson,fromDbFO);





        ClientCompany clientCompany = getClientCompany();
        clientCompany.addManger(fromDbFO.getClientId());


        System.out.println("PO pred pridanim do databazy");
        System.out.println(clientCompany);


        Client fromDBPO = this.clientService.addNewClientCompany(clientCompany);

        assert fromDBPO != null;

        System.out.println("PO nova Z databazy");
        System.out.println(fromDBPO);

        clientCompany.setClientId(fromDBPO.getClientId());

        Assert.assertEquals(clientCompany,fromDBPO);


        System.out.println("FO po pridani PO");
        Optional<Client> getFOOpt = this.clientService.getClientById(fromDbFO.getClientId());

        assert getFOOpt.isPresent();

        System.out.println(getFOOpt.get());


        Assert.assertNotEquals(getFOOpt.get(),fromDbFO);



        ClientCompany cl = (ClientCompany) fromDBPO;
        cl.setManagers(new LinkedList<>());

        cl = this.clientService.updateClientCompany(cl);

        System.out.println("PO po update");
        System.out.println(cl);

        Optional<Client> getFOOpt2 = this.clientService.getClientById(fromDbFO.getClientId());
        System.out.println("FO po update po");
        System.out.println(getFOOpt2.get());

        Assert.assertEquals(getFOOpt2.get(),fromDbFO);



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
        clientCompany.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        clientCompany.setNote("Prvy testovaci klient");
        clientCompany.setIco("25655225");
        clientCompany.setDic("525252525");
        clientCompany.setDicDph("2525252");
        clientCompany.setBussinesObject("Produkcia");


        return clientCompany;
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
}
