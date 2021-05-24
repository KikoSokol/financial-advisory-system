package sk.stu.fei.uim.bp.application.backend.contracts.service;

import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.backend.file.utils.ContractFileAttachment;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ContractService
{

    Contract addNewContractDocument(@NotNull ContractDocument newContractDocument, @NotNull ObjectId agentId);

    Contract addNewContractDocument(@NotNull ContractDocument newContractDocument,
                                    @NotNull List<FileWrapper> documentAttachments, @NotNull ObjectId agentId);

    Contract updateContractDocument(@NotNull Contract contract, @NotNull ContractDocument contractDocumentToUpdate);

    Contract updateContractDocument(@NotNull Contract contract, @NotNull ContractDocument contractDocumentToUpdate, @Nullable List<FileWrapper> fileAttachments);

    List<Contract> getAllContractsOfAgent(@NotNull ObjectId agentId);

    List<ContractDocument> getAllCurrentVersionContractDocumentOfAgent(@NotNull ObjectId agentId);

    Optional<Contract> getContractById(@NotNull ObjectId contractId);

    Optional<ContractDocument> getCurrentVersionContractDocumentById(@NotNull ObjectId contractDocumentId);

    List<ContractDocument> getAllOldVersionOfContractDocument(@NotNull Contract contract);

    Optional<ContractDocument> getOldVersionContractDocumentById(@NotNull ObjectId oldVersionContractDocumentById);

    Optional<Contract> getContractByContractNumber(@NotNull String contractNumber);

    List<ContractFileAttachment> getFileAttachmentsOfContract(@NotNull Contract contract);

    Optional<InputStream> getFileAttachements(@NotNull ContractFileAttachment contractFileAttachment);

    boolean deleteContract(@NotNull Contract contractToDelete);

}
