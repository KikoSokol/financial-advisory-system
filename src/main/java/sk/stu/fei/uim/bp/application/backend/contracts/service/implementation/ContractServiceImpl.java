package sk.stu.fei.uim.bp.application.backend.contracts.service.implementation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.implementation.ClientRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.ContractDocumentRepository;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.ContractRepository;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.implementation.ContractDocumentRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.implementation.ContractRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.service.ContractService;
import sk.stu.fei.uim.bp.application.backend.file.repository.FileAttachmentRepository;
import sk.stu.fei.uim.bp.application.backend.file.repository.FileRepository;
import sk.stu.fei.uim.bp.application.backend.file.repository.implementation.FileAttachmentRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.file.repository.implementation.FileRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;
import sk.stu.fei.uim.bp.application.backend.file.utils.ContractFileAttachment;
import sk.stu.fei.uim.bp.application.backend.file.utils.FileAttachment;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService
{
    private final ContractRepository contractRepository;
    private final ContractDocumentRepository contractDocumentRepository;
    private final FileRepository fileRepository;
    private final FileAttachmentRepository fileAttachmentRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ContractServiceImpl(ContractRepositoryImpl contractRepository,
                               ContractDocumentRepositoryImpl contractDocumentRepository,
                               FileRepositoryImpl fileRepository,
                               FileAttachmentRepositoryImpl fileAttachmentRepository,
                               ClientRepositoryImpl clientRepository)
    {
        this.contractRepository = contractRepository;
        this.contractDocumentRepository = contractDocumentRepository;
        this.fileRepository = fileRepository;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Contract addNewContractDocument(@NotNull ContractDocument newContractDocument, @NotNull ObjectId agentId)
    {
        return uploadNewContract(newContractDocument,null,agentId);
    }

    @Override
    public Contract addNewContractDocument(@NotNull ContractDocument newContractDocument,
                                           @NotNull List<FileWrapper> documentAttachments, @NotNull ObjectId agentId)
    {
        return uploadNewContract(newContractDocument,documentAttachments,agentId);
    }


    private Contract uploadNewContract(@NotNull ContractDocument newContractDocument,
                                       @Nullable List<FileWrapper> documentAttachments, @NotNull ObjectId agentId)
    {
        int versionOfDocument = 1;

        newContractDocument.setVersion(versionOfDocument);

        ContractDocument contractDocument = this.contractDocumentRepository.addNewVersionOfContractDocument(newContractDocument);

        Contract contract = new Contract();
        contract.setContractNumber(contractDocument.getContractNumber());
        contract.setAgent(agentId);
        contract.setCurrentVersion(contractDocument.getContractDocumentId());

        if(documentAttachments != null)
        {
            List<ObjectId> attachments = uploadNewContractFileAttachments(documentAttachments,versionOfDocument);
            contract.setFileAttachments(attachments);
        }

        Contract uploadedContract =  this.contractRepository.addNewContract(contract);

        if(uploadedContract != null)
            addContractToClient(contractDocument.getOwner(),uploadedContract);

        return uploadedContract;

    }

    private void addContractToClient(ObjectId clientId,Contract contract)
    {
        Client client = this.clientRepository.getClientById(clientId).get();
        this.clientRepository.addContractForClient(client,contract.getContractId());
    }


    private List<ObjectId> uploadNewContractFileAttachments(List<FileWrapper> documentAttachments, int version)
    {
        List<ObjectId> attachments = new LinkedList<>();

        for(FileWrapper documentAttachment : documentAttachments)
        {
            ContractFileAttachment uploadedAttachment = this.uploadFileAttachment(documentAttachment,version);
            attachments.add(uploadedAttachment.getFileAttachmentId());
        }

        return attachments;
    }

    private ContractFileAttachment uploadFileAttachment(FileWrapper attachment, int version)
    {
        String fileName = attachment.getFileData().getFileName();

        if(attachment.isSetCustomFileName())
            fileName = attachment.getCustomFileName();

        ObjectId fileId = this.uploadNewContractFileAttachment(attachment,fileName);

        ContractFileAttachment contractFileAttachment = new ContractFileAttachment(fileId,fileName,version);

        return (ContractFileAttachment) this.fileAttachmentRepository.addNewFileAttachment(contractFileAttachment);
    }

    private ObjectId uploadNewContractFileAttachment(FileWrapper attachment, String fileName)
    {
        DBObject metaData = new BasicDBObject();
        metaData.put("uploadName",attachment.getFileData().getFileName());

        return this.fileRepository.saveFile(attachment.getFile(),attachment.getFileData(),fileName,metaData);
    }

    @Override
    public Contract updateContractDocument(@NotNull Contract contract, @NotNull ContractDocument contractDocumentToUpdate)
    {
        return updateContract(contract,contractDocumentToUpdate,null);
    }

    @Override
    public Contract updateContractDocument(@NotNull Contract contract, @NotNull ContractDocument contractDocumentToUpdate, @Nullable List<FileWrapper> fileAttachments)
    {
        return updateContract(contract,contractDocumentToUpdate,fileAttachments);
    }

    private Contract updateContract(@NotNull Contract contract, @NotNull ContractDocument contractDocumentToUpdate, @Nullable List<FileWrapper> fileAttachments)
    {
        Optional<ContractDocument> contractDocumentOptional = this.contractDocumentRepository.getCurrentVersionOfContractDocumentById(contract.getCurrentVersion());
        ContractDocument contractDocument = contractDocumentOptional.get();

        int newVersion = contractDocument.getVersion() + 1;
        contractDocumentToUpdate.setVersion(newVersion);

        contractDocument.setContractDocumentId(null);

        ContractDocument oldVersionContractDocument = this.contractDocumentRepository.addOldVersionOfContractDocument(contractDocument);

        ContractDocument newVersionContractDocument = this.contractDocumentRepository.updateCurrentVersionOfContractDocument(contractDocumentToUpdate);

        contract.getOldVersions().add(oldVersionContractDocument.getContractDocumentId());
        contract.setCurrentVersion(newVersionContractDocument.getContractDocumentId());

        if(fileAttachments != null)
        {
            List<ObjectId> attachments = uploadNewContractFileAttachments(fileAttachments,newVersion);
            contract.getFileAttachments().addAll(attachments);
        }

        return this.contractRepository.updateContract(contract);
    }

    @Override
    public List<Contract> getAllContractsOfAgent(@NotNull ObjectId agentId)
    {
        return this.contractRepository.getAllContractsOfCurrentAgent(agentId);
    }

    @Override
    public List<ContractDocument> getAllCurrentVersionContractDocumentOfAgent(@NotNull ObjectId agentId)
    {
        List<Contract> contracts = getAllContractsOfAgent(agentId);

        List<ObjectId> contractDocumentIds = new LinkedList<>();

        for(Contract contract : contracts)
        {
            contractDocumentIds.add(contract.getCurrentVersion());
        }

        return this.contractDocumentRepository.getAllCurrentVersionOfContractDocumentByListOfId(contractDocumentIds);
    }

    @Override
    public Optional<Contract> getContractById(@NotNull ObjectId contractId)
    {
        return this.contractRepository.getContractById(contractId);
    }

    @Override
    public Optional<ContractDocument> getCurrentVersionContractDocumentById(@NotNull ObjectId contractDocumentId)
    {
        return this.contractDocumentRepository.getCurrentVersionOfContractDocumentById(contractDocumentId);
    }

    @Override
    public List<ContractDocument> getAllOldVersionOfContractDocument(@NotNull Contract contract)
    {
        return this.contractDocumentRepository.getAllOldVersionOfContractDocumentByListOfId(contract.getOldVersions());
    }

    @Override
    public Optional<ContractDocument> getOldVersionContractDocumentById(@NotNull ObjectId oldVersionContractDocumentById)
    {
        return this.contractDocumentRepository.getOldVersionOfContractDocumentById(oldVersionContractDocumentById);
    }

    @Override
    public Optional<Contract> getContractByContractNumber(@NotNull String contractNumber)
    {
        return this.contractRepository.getContractByContractNumber(contractNumber);
    }

    @Override
    public List<ContractFileAttachment> getFileAttachmentsOfContract(@NotNull Contract contract)
    {
        List<FileAttachment> fileAttachments = this.fileAttachmentRepository.getFileAttachmentByListOfId(contract.getFileAttachments());

        List<ContractFileAttachment> attachmentList = new LinkedList<>();

        for(FileAttachment attachment : fileAttachments)
        {
            ContractFileAttachment contractFileAttachment = (ContractFileAttachment) attachment;
            contractFileAttachment.setFile(this.fileRepository.getInformationAboutFile(contractFileAttachment.getFileInDbId()));
            attachmentList.add(contractFileAttachment);
        }

        return attachmentList;
    }

    @Override
    public Optional<InputStream> getFileAttachements(@NotNull ContractFileAttachment contractFileAttachment)
    {
        try {
            return Optional.ofNullable(this.fileRepository.getFile(contractFileAttachment.getFileInDbId()));
        } catch (IOException e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean deleteContract(@NotNull Contract contractToDelete)
    {
        boolean correctDelete = this.contractRepository.deleteContract(contractToDelete);

        if(!correctDelete)
            return false;

        removeContractFromOwnerList(contractToDelete);
        deleteCurrentVersionContractDocument(contractToDelete.getCurrentVersion());
        deleteAllOldVersionContractDocument(contractToDelete.getOldVersions());
        deleteAllFileAttachmentsOfContract(contractToDelete);


        return true;

    }


    private void removeContractFromOwnerList(Contract contractToRemove)
    {
        ContractDocument contractDocument = this.contractDocumentRepository.getCurrentVersionOfContractDocumentById(contractToRemove.getCurrentVersion()).get();

        Client client = this.clientRepository.getClientById(contractDocument.getOwner()).get();

        this.clientRepository.removeContractFromClient(client, contractToRemove.getContractId());
    }

    private boolean deleteCurrentVersionContractDocument(ObjectId contractDocumentId)
    {
        Optional<ContractDocument> contractDocumentOptional = this.contractDocumentRepository.getCurrentVersionOfContractDocumentById(contractDocumentId);
        ContractDocument contractDocument = contractDocumentOptional.get();

        return this.contractDocumentRepository.deleteCurrentVersionOfContractDocument(contractDocument);
    }

    private boolean deleteAllOldVersionContractDocument(List<ObjectId> contractDocumentIds)
    {
        return this.contractDocumentRepository.deleteAllOldVersionByListOfIdOfContractDocument(contractDocumentIds);
    }

    private boolean deleteAllFileAttachmentsOfContract(Contract contract)
    {
        List<ContractFileAttachment> fileAttachments = getFileAttachmentsOfContract(contract);

        return this.fileAttachmentRepository.deleteAllFileAttachmentsByList(new LinkedList<>(fileAttachments));
    }







}
