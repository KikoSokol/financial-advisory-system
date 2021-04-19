package sk.stu.fei.uim.bp.application.backend.contracts.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.companyAndProduct.web.dto.ProductDto;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public abstract class ContractDocumentDto
{
    private ObjectId contractDocumentId;

    @NotBlank(message = "Číslo zmluvy je povinný údaj")
    private String contractNumber;

    @NotNull(message = "Vlastník zmluvy je povinný údaj")
    private ClientDto owner;

    @NotNull(message = "Produkt je povinný údaj")
    private ProductDto product;

    @NotNull(message = "Dátum začiatku platnosti je povinný údaj")
    private LocalDate dateOfStart;

    private LocalDate dateOfEnd;

    private String note;

    @NotNull
    private int version;


    public ContractDocumentDto(ContractDocument contractDocument, ClientDto owner, ProductDto productDto)
    {
        setContractDocumentId(contractDocument.getContractDocumentId());
        setContractNumber(contractDocument.getContractNumber());
        setOwner(owner);
        setProduct(productDto);
        setDateOfStart(contractDocument.getDateOfStart());
        setDateOfEnd(contractDocument.getDateOfEnd());
        setNote(contractDocument.getNote());
        setVersion(contractDocument.getVersion());
    }

    public ContractDocument toContractDocument(ContractDocument contractDocument)
    {
        contractDocument.setContractDocumentId(getContractDocumentId());
        contractDocument.setContractNumber(getContractNumber());
        contractDocument.setOwner(getOwner().getClientId());
        contractDocument.setProduct(getProduct().getProductId());
        contractDocument.setDateOfStart(getDateOfStart());
        contractDocument.setDateOfEnd(getDateOfEnd());
        contractDocument.setNote(getNote());
        contractDocument.setVersion(getVersion());

        return contractDocument;
    }
}
