package sk.stu.fei.uim.bp.application.backend.clientAndProduct.web.dto;

import lombok.Data;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain.Company;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;

@Data
public class CompanyDto
{
    private ObjectId companyId;

    @NotBlank(message = "Meno spoločnosti je povinné pole")
    private String name;

    @NotBlank(message = "IČO je povinné pole")
    @Pattern(regexp = "[0-9]{8}", message = "IČO musí byť 8 miestý číselný kód")
    private String ico;

    @NotBlank(message = "Ulica je povinné pole")
    private String street;

    @NotBlank(message = "Číslo domu je povinné pole")
    @Pattern(regexp = "[0-9]*[/]?[0-9]{1,}")
    private String numberOfHouse;


    @NotBlank(message = "PSČ je povinné pole")
    @Pattern(regexp = "[0-9]{3}[\\s]?[0-9]{2}")
    private String postalCode;


    @NotBlank(message = "Mesto je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Mesto musí obsahovať iba platné znaky")
    private String city;

    @NotBlank(message = "Krajina je povinné pole")
    @Pattern(regexp = "[^0-9]{1,}", message = "Krajina musí obsahovať iba platné znaky")
    private String state;

    private List<ObjectId> products;



    public CompanyDto(Company company)
    {
        setCompanyId(company.getCompanyId());
        setName(company.getName());
        setIco(company.getIco());

        if(company.getAddress() == null)
        {
            company.setAddress(new Address());
        }
        setAddress(company.getAddress());
        setProducts(company.getProducts());
    }


    private void setAddress(Address companyAddress)
    {
        setStreet(companyAddress.getStreet());
        setNumberOfHouse(companyAddress.getNumberOfHouse());
        setPostalCode(companyAddress.getPostalCode());
        setCity(companyAddress.getCity());
        setState(companyAddress.getState());
    }

    private Address toAddress()
    {
        Address companyAddress = new Address();
        companyAddress.setStreet(this.street);
        companyAddress.setNumberOfHouse(this.numberOfHouse);
        companyAddress.setPostalCode(this.postalCode);
        companyAddress.setCity(this.city);
        companyAddress.setState(this.state);

        return companyAddress;
    }


    public Company toCompany(Company company)
    {
        company.setCompanyId(this.companyId);
        company.setName(this.name);
        company.setIco(this.ico);
        company.setAddress(toAddress());
        company.setProducts(this.products);

        return company;

    }

    public String getFullAddress()
    {
        return street + " " + numberOfHouse + ", " + postalCode + " " + city + " " + state;
    }

}
