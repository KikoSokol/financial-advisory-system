package sk.stu.fei.uim.bp.application.backend.contracts.web.filters;

import org.apache.commons.lang3.StringUtils;
import sk.stu.fei.uim.bp.application.backend.contracts.web.table.TableContractItem;

import java.time.LocalDate;

public class ContractFilter
{

    private String contractNumber;
    private String ownerFullName;
    private String insuredFullName;
    private String companyName;
    private String productName;
    private LocalDate anniversaryDate;
    private String numberOfVehicle;

    public ContractFilter()
    {
        setContractNumber("");
        setOwnerFullName("");
        setInsuredFullName("");
        setCompanyName("");
        setProductName("");
        setAnniversaryDate(null);
        setNumberOfVehicle("");
    }


    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public void setInsuredFullName(String insuredFullName) {
        this.insuredFullName = insuredFullName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAnniversaryDate(LocalDate anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public void setNumberOfVehicle(String numberOfVehicle) {
        this.numberOfVehicle = numberOfVehicle;
    }

    public boolean test(TableContractItem item)
    {
        if(this.contractNumber.length() > 0 && !StringUtils.containsIgnoreCase(item.getContractNumber(),this.contractNumber))
        {
            return false;
        }

        if(this.ownerFullName.length() > 0 && !StringUtils.containsIgnoreCase(item.getOwnerFullName(),this.ownerFullName))
        {
            return false;
        }

        if(this.insuredFullName.length() > 0 && !StringUtils.containsIgnoreCase(item.getInsuredFullName(),this.insuredFullName))
        {
            return false;
        }

        if(this.companyName.length() > 0 && !StringUtils.containsIgnoreCase(item.getCompanyName(),this.companyName))
        {
            return false;
        }


        if(this.productName.length() > 0 && !StringUtils.containsIgnoreCase(item.getProductName(),this.productName))
        {
            return false;
        }

        if(this.anniversaryDate != null && !compareDate(item.getAnniversaryDate()))
        {
            return false;
        }

        if(this.numberOfVehicle.length() > 0 && !StringUtils.containsIgnoreCase(item.getNumberOfVehicle(),this.numberOfVehicle))
        {
            return false;
        }

       return true;
    }


    private boolean compareDate(LocalDate itemDate)
    {
        LocalDate thisDatePlusThreeMonths = this.anniversaryDate.plusMonths(3);
        LocalDate thisDatePlusThreeMonthsAndOneWeek = thisDatePlusThreeMonths.plusWeeks(1);

        return itemDate.isAfter(thisDatePlusThreeMonths.minusDays(1)) && itemDate.isBefore(thisDatePlusThreeMonthsAndOneWeek.plusDays(1));
    }



}
