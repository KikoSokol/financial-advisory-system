package sk.stu.fei.uim.bp.application.backend.contracts.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Document("ContractDocument_current_version")
public abstract class ContractDocument
{
    @MongoId
    private ObjectId contractDocumentId;

    @NotBlank(message = "Číslo zmluvy je povinný údaj")
    private String contractNumber;

    @NotNull(message = "Vlastník zmluvy je povinný údaj")
    private ObjectId owner;

    @NotNull(message = "Produkt je povinný údaj")
    private ObjectId product;

    @NotNull(message = "Dátum začiatku platnosti je povinný údaj")
    private LocalDate dateOfStart;

    private LocalDate dateOfEnd;

    private String note;

    @NotNull
    private int version;


    @Override
    public String toString() {
        return super.toString() + "\n ContractDocument{" +
                "\n contractDocumentId=" + contractDocumentId +
                "\n contractNumber='" + contractNumber + '\'' +
                "\n owner=" + owner +
                "\n product=" + product +
                "\n dateOfStart=" + dateOfStart +
                "\n dateOfEnd=" + dateOfEnd +
                "\n note='" + note + '\'' +
                "\n version=" + version +
                '}';
    }
}
