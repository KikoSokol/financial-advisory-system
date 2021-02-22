package sk.stu.fei.uim.bp.application;

import org.bson.types.ObjectId;
import org.junit.Test;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;

public class TestClientDomain
{

    @Test
    public void test()
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

        System.out.println(clientCompany);

        Client c = clientCompany;

        System.out.println(c);
    }
}
