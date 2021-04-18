package sk.stu.fei.uim.bp.application.contractTest;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.LifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.PaymentFrequency;
import sk.stu.fei.uim.bp.application.backend.contracts.service.ContractService;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContractTest
{

    @Autowired
    private ContractService contractService;


    @Test
    public void test()
    {
        ObjectId agentId = new ObjectId("601b6300dbf3207494372a20");

        LifeInsurance lfToUpoad = createLifeInsurance();
        Contract uploadedContractLf = this.contractService.addNewContractDocument(lfToUpoad,agentId);


        Optional<ContractDocument> lfOptional = this.contractService.getCurrentVersionContractDocumentById(uploadedContractLf.getCurrentVersion());

        assert lfOptional.isPresent();

        LifeInsurance uploadedLf = (LifeInsurance) lfOptional.get();

        Assert.assertEquals(lfToUpoad,uploadedLf);

        System.out.println("lfToUpload");
        System.out.println(lfOptional);
        System.out.println();
        System.out.println();
        System.out.println("uploadedContractLf");
        System.out.println(uploadedContractLf);
        System.out.println();
        System.out.println();
        System.out.println("uploadedLf");
        System.out.println(uploadedLf);

        System.out.println("///////////////////////////////");
        System.out.println("///////////////////////////////");


        LifeInsurance toUpdate = copy(uploadedLf);
        toUpdate.setNote("updatovana lf");
        toUpdate.setAnniversaryDate(toUpdate.getAnniversaryDate().plusYears(1));

        Contract updatedContractLf = this.contractService.updateContractDocument(uploadedContractLf,toUpdate);

        Optional<ContractDocument> updatedLfOptional = this.contractService.getOldVersionContractDocumentById(updatedContractLf.getOldVersions().get(0));

        ContractDocument oldVersionLf = updatedLfOptional.get();


        System.out.println("toUpdate");
        System.out.println(toUpdate);
        System.out.println();
        System.out.println();
        System.out.println("updatedContractLf");
        System.out.println(updatedContractLf);
        System.out.println();
        System.out.println();
        System.out.println("updatedLf");
        System.out.println(oldVersionLf);


        Assert.assertEquals(lfToUpoad,(LifeInsurance) oldVersionLf);


    }



    @Test
    public void testWithFile()
    {
        ObjectId agentId = new ObjectId("601b6300dbf3207494372a20");

        LifeInsurance lfToUpoad = createLifeInsurance();
        Contract uploadedContractLf = this.contractService.addNewContractDocument(lfToUpoad,agentId);


        Optional<ContractDocument> lfOptional = this.contractService.getCurrentVersionContractDocumentById(uploadedContractLf.getCurrentVersion());

        assert lfOptional.isPresent();

        LifeInsurance uploadedLf = (LifeInsurance) lfOptional.get();

        Assert.assertEquals(lfToUpoad,uploadedLf);

        System.out.println("lfToUpload");
        System.out.println(lfOptional);
        System.out.println();
        System.out.println();
        System.out.println("uploadedContractLf");
        System.out.println(uploadedContractLf);
        System.out.println();
        System.out.println();
        System.out.println("uploadedLf");
        System.out.println(uploadedLf);

        System.out.println("///////////////////////////////");
        System.out.println("///////////////////////////////");


        LifeInsurance toUpdate = copy(uploadedLf);
        toUpdate.setNote("updatovana lf");
        toUpdate.setAnniversaryDate(toUpdate.getAnniversaryDate().plusYears(1));

        Contract updatedContractLf = this.contractService.updateContractDocument(uploadedContractLf,toUpdate);

        Optional<ContractDocument> updatedLfOptional = this.contractService.getOldVersionContractDocumentById(updatedContractLf.getOldVersions().get(0));

        ContractDocument oldVersionLf = updatedLfOptional.get();


        System.out.println("toUpdate");
        System.out.println(toUpdate);
        System.out.println();
        System.out.println();
        System.out.println("updatedContractLf");
        System.out.println(updatedContractLf);
        System.out.println();
        System.out.println();
        System.out.println("updatedLf");
        System.out.println(oldVersionLf);


        Assert.assertEquals(lfToUpoad,(LifeInsurance) oldVersionLf);


    }








    public LifeInsurance createLifeInsurance()
    {
        LifeInsurance lifeInsurance = new LifeInsurance();

        lifeInsurance.setContractNumber("12345678");
        lifeInsurance.setInsured(new ObjectId("604e8ae101fb9814228849ad"));
        lifeInsurance.setVersion(1);
        lifeInsurance.setAnniversaryDate(LocalDate.now().plusYears(1));
        lifeInsurance.setDateOfStart(LocalDate.now());
        lifeInsurance.setNote("first test life insurance");
        lifeInsurance.setOwner(new ObjectId("604e8ae101fb9814228849ad"));
        lifeInsurance.setPayment(25.25);
        lifeInsurance.setPaymentFrequency(PaymentFrequency.MONTHLY);
        lifeInsurance.setProduct(new ObjectId("604e9102b0799303b68cf6b3"));

        // bez date of end

        return lifeInsurance;

    }


    private LifeInsurance copy(LifeInsurance lifeInsurance)
    {
        LifeInsurance lifeInsurance1 = new LifeInsurance();

        lifeInsurance.setContractDocumentId(lifeInsurance.getContractDocumentId());
        lifeInsurance1.setContractNumber(lifeInsurance.getContractNumber());
        lifeInsurance1.setInsured(lifeInsurance.getInsured());
        lifeInsurance1.setVersion(lifeInsurance.getVersion());
        lifeInsurance1.setAnniversaryDate(lifeInsurance.getAnniversaryDate());
        lifeInsurance1.setDateOfStart(lifeInsurance.getDateOfStart());
        lifeInsurance1.setNote(lifeInsurance.getNote());
        lifeInsurance1.setOwner(lifeInsurance.getOwner());
        lifeInsurance1.setPayment(lifeInsurance.getPayment());
        lifeInsurance1.setPaymentFrequency(lifeInsurance.getPaymentFrequency());
        lifeInsurance1.setProduct(lifeInsurance.getProduct());

        return lifeInsurance1;

    }








}
