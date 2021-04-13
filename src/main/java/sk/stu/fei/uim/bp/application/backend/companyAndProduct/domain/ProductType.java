package sk.stu.fei.uim.bp.application.backend.companyAndProduct.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document("ProductType")
public class ProductType
{
    @MongoId
    private ObjectId productTypeId;

    @NotNull(message = "Kategória  je povinné pole")
    private ProductTypeCategory category;

    @NotBlank(message = "Meno typu produktu je povinné pole")
    private String name;

    @NotNull
    private ObjectId agent;


    @Override
    public String toString() {
        return "ProductType{" +
                "\n productTypeId= " + productTypeId +
                "\n category= " + category +
                "\n name= '" + name + '\'' +
                "\n agent= " + agent +
                '}';
    }
}
