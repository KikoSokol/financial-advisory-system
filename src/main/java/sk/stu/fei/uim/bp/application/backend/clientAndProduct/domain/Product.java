package sk.stu.fei.uim.bp.application.backend.clientAndProduct.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document("Product")
public class Product
{
    @MongoId
    private ObjectId productId;

    @NotNull(message = "Typ produktu je povinné pole")
    private ObjectId productType;

    @NotBlank(message = "Meno produktu je povinné pole")
    private String name;

    @NotNull(message = "Spoločnosť je povinné pole")
    private ObjectId company;

    @NotNull
    private ObjectId agent;


    @Override
    public String toString() {
        return "Product{" +
                "productId= " + productId +
                "\n productType= " + productType +
                "\n name= '" + name + '\'' +
                "\n company= " + company +
                "\n agent= " + agent +
                '}';
    }
}
