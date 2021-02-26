package sk.stu.fei.uim.bp.application;

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
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientCompanyRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.PhysicalPersonRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCliientRepository
{
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientCompanyRepository clientCompanyRepository;

    @Autowired
    PhysicalPersonRepository personRepository;
//    @Autowired
//    ClientRepositoryImpl clientRepository;

//    @Autowired
//    public TestCliientRepository(ClientRepositoryImpl clientRepository)
//    {
////        System.out.println("ahoj");
////        this.clientRepository = clientRepository;
////        this.clientCompanyRepository = clientRepository;
////        this.personRepository = clientRepository;
//        this.clientRepository = clientRepository;
//    }

    @Test
    public void testAddClientCompany()
    {
        ClientCompany clientCompany = getClientCompany();

        System.out.println("pred pridanim do databazy");
        System.out.println(clientCompany);

        Client fromDB = this.clientRepository.addClient(clientCompany);

        assert fromDB != null;

        System.out.println("Z databazy");
        System.out.println(fromDB);

        clientCompany.setClientId(fromDB.getClientId());

        Assert.assertEquals(clientCompany,fromDB);

        Optional<Client> getById = this.clientRepository.getClientById(fromDB.getClientId());

        assert getById.isPresent();

        Assert.assertEquals(fromDB,getById.get());


        ObjectId o = new ObjectId();
        Client c = this.clientCompanyRepository.addManagerToClientCompany((ClientCompany) fromDB,o);
        System.out.println("S pridanym manazerom");
        System.out.println(c);

        Assert.assertNotEquals(c,fromDB);

        Client c2 = this.clientCompanyRepository.removeManagerFromClientCompany((ClientCompany) c,o);

        System.out.println("S odobranym manazerom");
        System.out.println(c2);

        Assert.assertEquals(c2,fromDB);







        ObjectId contract = new ObjectId();

        Client withContract = this.clientRepository.addContractForClient(fromDB,contract);
        System.out.println("pridana zmluva");
        System.out.println(withContract);
        System.out.println("from db");
        System.out.println(fromDB);


        Optional<Client> tmpWithContract = this.clientRepository.getClientById(fromDB.getClientId());
        Assert.assertEquals(tmpWithContract.get(),withContract);
        Assert.assertArrayEquals(tmpWithContract.get().getContracts().toArray(),withContract.getContracts().toArray());


        ObjectId contract2 = new ObjectId();
        Client withContract2 = this.clientRepository.addContractForClient(fromDB,contract2);
        System.out.println("pridana zmluva2");
        System.out.println(withContract2);
        System.out.println("from db");
        System.out.println(fromDB);
        this.clientRepository.removeContractFromClient(withContract2,contract2);




        Client delContract = this.clientRepository.removeContractFromClient(withContract,contract);
        System.out.println("Odstraneny contract");
        System.out.println(delContract);
        Assert.assertEquals(delContract,fromDB);


        Client delContract2 = this.clientRepository.removeContractFromClient(withContract,contract);
        System.out.println("Odstraneny contract2");
        System.out.println(delContract2);
        Assert.assertEquals(delContract2,fromDB);


        System.out.println("Pred upravou");
        System.out.println(fromDB);
        fromDB.setNote("dufam ze sa updatolo");
        Client up = this.clientRepository.updateClient(fromDB);
        System.out.println("po uprave");
        System.out.println(up);
        Optional<Client> up2 = this.clientRepository.getClientById(up.getClientId());
        Assert.assertEquals(up,up2.get());
        Assert.assertArrayEquals(up.getContracts().toArray(),up2.get().getContracts().toArray());
        ClientCompany cc = (ClientCompany) up;
        Assert.assertArrayEquals(cc.getManagers().toArray(),((ClientCompany) up2.get()).getManagers().toArray());




        this.clientRepository.deleteClient(fromDB);
        Optional<Client> v = this.clientRepository.getClientById(fromDB.getClientId());

        assert v.isEmpty();



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


        return clientCompany;
    }
}
