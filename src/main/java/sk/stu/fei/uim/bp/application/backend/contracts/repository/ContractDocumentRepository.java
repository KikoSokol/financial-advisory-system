package sk.stu.fei.uim.bp.application.backend.contracts.repository;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ContractDocumentRepository
{
    ContractDocument addNewVersionOfContractDocument(@NotNull ContractDocument newContractDocument);

    ContractDocument updateCurrentVersionOfContractDocument(@NotNull ContractDocument contractDocumentToUpdate);

    ContractDocument addOldVersionOfContractDocument(@NotNull ContractDocument oldVersionContractDocument);

    boolean deleteCurrentVersionOfContractDocument(@NotNull ContractDocument currentVersionOfContractDocumentToDelete);

    boolean deleteOldVersionOfContractDocument(@NotNull ContractDocument oldVersionOfContractDocumentToDelete);

    boolean deleteAllOldVersionByListOfIdOfContractDocument(@NotNull List<ObjectId> oldVersionOfContractDocumentIds);

    Optional<ContractDocument> getCurrentVersionOfContractDocumentById(@NotNull ObjectId contractDocumentId);

    Optional<ContractDocument> getOldVersionOfContractDocumentById(@NotNull ObjectId contractDocumentId);

    List<ContractDocument> getAllCurrentVersionOfContractDocumentByListOfId(@NotNull List<ObjectId> contractDocumentIds);

    List<ContractDocument> getAllOldVersionOfContractDocumentByListOfId(@NotNull List<ObjectId> contractDocumentIds);

    List<ContractDocument> getAllCurrentVersionOfContractDocumentByInsuredId(@NotNull ObjectId insuredId);

    List<ContractDocument> getAllCurrentVersionOfContractDocumentByProductId(@NotNull ObjectId productId);
}
