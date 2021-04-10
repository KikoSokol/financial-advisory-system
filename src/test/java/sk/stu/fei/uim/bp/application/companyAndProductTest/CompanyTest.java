package sk.stu.fei.uim.bp.application.companyAndProductTest;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Company;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.repository.CompanyRepository;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.service.CompanyService;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyTest
{

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void test()
    {
        Company insert1 = getCompany1();

        System.out.println("insert1");
        System.out.println(insert1);

        Company inserted1 = this.companyService.addNewCompany(insert1);

        assert inserted1 != null;

        System.out.println("inserted");
        System.out.println(inserted1);

        insert1.setCompanyId(inserted1.getCompanyId());

        Assert.assertEquals(insert1,inserted1);

        Optional<Company> opt1 = this.companyService.getCompanyById(inserted1.getCompanyId());

        assert opt1.isPresent();

        System.out.println("inserted1 byId");
        System.out.println(opt1.get());


        Assert.assertEquals(opt1.get(),inserted1);




        Company inserted2 = this.companyService.addNewCompany(getCompany2());

        assert inserted2 != null;

        System.out.println();
        System.out.println("inserted 2");
        System.out.println(inserted2);
        System.out.println();



        Company update1 = this.companyRepository.addProductForCompany(inserted1,new ObjectId());
        Company update2 = this.companyRepository.addProductForCompany(inserted2,new ObjectId());


        List<Company> byAgentId = this.companyService.getAllCompanyByCurrentAgent(new ObjectId("601b6300dbf3207494372a20"));

        assert !byAgentId.isEmpty();

        System.out.println("all by agent id");
        System.out.println(byAgentId);


        System.out.println();
        System.out.println();

        this.companyRepository.removeProductFromCompany(update1,update1.getProducts().get(0));
        this.companyRepository.removeProductFromCompany(update2,update2.getProducts().get(0));


        System.out.println("vymazane produkty");
        System.out.println(this.companyService.getAllCompanyByCurrentAgent(new ObjectId("601b6300dbf3207494372a20")));







    }


    public Company getCompany1()
    {
        Company company = new Company();
        company.setAddress(getAddress1());
        company.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        company.setIco("12345678");
        company.setName("AXA");

        return company;
    }

    public Company getCompany2()
    {
        Company company = new Company();
        company.setAddress(getAddress2());
        company.setAgent(new ObjectId("601b6300dbf3207494372a20"));
        company.setIco("87654321");
        company.setName("GENERALI");

        return company;
    }




    public Address getAddress1()
    {
        Address address = new Address();
        address.setStreet("bajkalska");
        address.setNumberOfHouse("5");
        address.setPostalCode("08001");
        address.setCity("Presov");
        address.setState("Slovakia");

        return address;
    }

    public Address getAddress2()
    {
        Address address = new Address();
        address.setStreet("Neviem aka");
        address.setNumberOfHouse("5");
        address.setPostalCode("08001");
        address.setCity("Bratislava");
        address.setState("Slovakia");

        return address;
    }

}
