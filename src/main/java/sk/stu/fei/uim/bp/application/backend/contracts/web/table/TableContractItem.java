package sk.stu.fei.uim.bp.application.backend.contracts.web.table;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.web.dto.*;

import java.time.LocalDate;

public class TableContractItem
{
    private ObjectId contractId;
    private String contractNumber;
    private String ownerFullName;
    private String insuredFullName;
    private String companyName;
    private String productName;
    private LocalDate anniversaryDate;
    private String numberOfVehicle;

    public TableContractItem(ContractDocumentDto contractDocumentDto)
    {
        if(contractDocumentDto instanceof LifeInsuranceDto)
        {
            this.initLifeInsuranceItem((LifeInsuranceDto) contractDocumentDto);
        }
        else if(contractDocumentDto instanceof VehicleInsuranceDto)
        {
            this.initVehicleInsuranceItem((VehicleInsuranceDto) contractDocumentDto);
        }
        else if(contractDocumentDto instanceof NonLifeInsuranceDto)
        {
            this.initNonLifeInsuranceItem((NonLifeInsuranceDto) contractDocumentDto);
        }

    }

    public void initLifeInsuranceItem(LifeInsuranceDto lifeInsuranceDto)
    {
        initContractDocumentDto(lifeInsuranceDto);
        initInsuranceDto(lifeInsuranceDto);
        this.numberOfVehicle = "";
    }

    public void initNonLifeInsuranceItem(NonLifeInsuranceDto nonLifeInsuranceDto)
    {
        initContractDocumentDto(nonLifeInsuranceDto);
        initInsuranceDto(nonLifeInsuranceDto);
        this.numberOfVehicle = "";
    }

    public void initVehicleInsuranceItem(VehicleInsuranceDto vehicleInsuranceDto)
    {
        initContractDocumentDto(vehicleInsuranceDto);
        initInsuranceDto(vehicleInsuranceDto);
        this.numberOfVehicle = vehicleInsuranceDto.getNumberOfVehicle();
    }

    private void initContractDocumentDto(ContractDocumentDto contractDocumentDto)
    {
        this.contractId = contractDocumentDto.getContractDocumentId();
        this.contractNumber = contractDocumentDto.getContractNumber();
        this.ownerFullName = contractDocumentDto.getOwner().getFullName();
        this.productName = contractDocumentDto.getProduct().getName();
        this.companyName = contractDocumentDto.getProduct().getCompanyName();
    }

    private void initInsuranceDto(InsuranceDto insuranceDto)
    {
        this.insuredFullName = insuranceDto.getInsured().getFullName();
        this.anniversaryDate = insuranceDto.getAnniversaryDate();
    }


    public ObjectId getContractId() {
        return contractId;
    }

    public void setContractId(ObjectId contractId) {
        this.contractId = contractId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public String getInsuredFullName() {
        return insuredFullName;
    }

    public void setInsuredFullName(String insuredFullName) {
        this.insuredFullName = insuredFullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(LocalDate anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public String getNumberOfVehicle() {
        return numberOfVehicle;
    }

    public void setNumberOfVehicle(String numberOfVehicle) {
        this.numberOfVehicle = numberOfVehicle;
    }
}
