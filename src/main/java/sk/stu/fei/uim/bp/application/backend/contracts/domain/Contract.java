package sk.stu.fei.uim.bp.application.backend.contracts.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Data
@Document("Contract")
public class Contract
{
    @MongoId
    private ObjectId contractId;

    @NotBlank(message = "Číslo zmluvy je povinný údaj")
    private String contractNumber;

    @NotNull(message = "Zmluva musí mať verziu")
    private ObjectId currentVersion;

    private List<ObjectId> oldVersions = new LinkedList<>();

    private List<ObjectId> fileAttachments = new LinkedList<>();

    @NotNull
    private ObjectId agent;

    @Override
    public String toString() {
        return "Contract{" +
                "\n contractId=" + contractId +
                "\n contractNumber='" + contractNumber + '\'' +
                "\n currentVersion=" + currentVersion +
                "\n oldVersions=" + oldVersions +
                "\n fileAttachments=" + fileAttachments +
                "\n agent=" + agent +
                '}';
    }
}
