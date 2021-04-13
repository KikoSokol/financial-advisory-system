package sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import sk.stu.fei.uim.bp.application.backend.address.Address;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;

@Data
@Document("Company")
public class Company
{
    @MongoId
    private ObjectId companyId;

    @NotBlank(message = "Meno spoločnosti je povinné pole")
    private String name;

    @NotBlank(message = "IČO je povinné pole")
    @Pattern(regexp = "[0-9]{8}", message = "IČO musí byť 8 miestý číselný kód")
    private String ico;

    @NotNull(message = "Adresa spoločnosti musí byť zadaná")
    private Address address;

    private List<ObjectId> products = new LinkedList<>();

    @NotNull
    private ObjectId agent;


    @Override
    public String toString() {
        return "Company{" +
                "\n companyId= " + companyId +
                "\n name= '" + name + '\'' +
                "\n ico= '" + ico + '\'' +
                "\n address= " + address +
                "\n products= " + products +
                "\n agent= " + agent +
                '}';
    }
}
