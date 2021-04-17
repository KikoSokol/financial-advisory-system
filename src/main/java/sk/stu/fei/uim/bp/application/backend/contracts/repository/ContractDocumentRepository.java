package sk.stu.fei.uim.bp.application.backend.contracts.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ContractDocumentRepository
{
    ContractDocument addNewVersionOfContractDocument(@NotNull ContractDocument newContractDocument);

    ContractDocument addOldVersionOfContractDocument(@NotNull ContractDocument oldVersionContractDocument);

    Optional<ContractDocument> getCurrentVersionOfContractDocumentById(@NotNull ObjectId contractDocumentId);

    Optional<ContractDocument> getOldVersionOfContractDocumentById(@NotNull ObjectId contractDocumentId);

    List<ContractDocument> getAllCurrentVersionOfContractDocumentByListOfId(@NotNull List<ObjectId> contractDocumentIds);

    List<ContractDocument> getAllOldVersionOfContractDocumentByListOfId(@NotNull List<ObjectId> contractDocumentIds);
}
