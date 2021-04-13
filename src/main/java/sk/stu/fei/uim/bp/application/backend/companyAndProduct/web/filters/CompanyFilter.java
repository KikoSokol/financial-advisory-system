package sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.filters;

import org.apache.commons.lang3.StringUtils;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.CompanyDto;


public class CompanyFilter
{
    private String name;
    private String ico;
    private String address;


    public CompanyFilter()
    {
        setName("");
        setIco("");
        setAddress("");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public boolean test(CompanyDto companyDto)
    {
        if(this.name.length() > 0 && !StringUtils.containsIgnoreCase(companyDto.getName(),this.name))
        {
            return false;
        }

        if(this.ico.length() > 0 && !StringUtils.containsIgnoreCase(companyDto.getIco(),this.ico))
        {
            return false;
        }

        if(this.address.length() > 0 && !StringUtils.containsIgnoreCase(companyDto.getFullAddress(),this.address))
        {
            return false;
        }

        return true;
    }
}
