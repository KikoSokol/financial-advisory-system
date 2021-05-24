package sk.stu.fei.uim.bp.application.backend.contracts.repository.implementation;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.ContractDocumentRepository;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class ContractDocumentRepositoryImpl implements ContractDocumentRepository
{

    private final String OLD_VERSION = "ContractDocument_old_version";

    private final MongoOperations mongoOperations;

    @Autowired
    public ContractDocumentRepositoryImpl(MongoTemplate mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }


    @Override
    public ContractDocument addNewVersionOfContractDocument(@NotNull ContractDocument newContractDocument)
    {
        return this.mongoOperations.insert(newContractDocument);
    }

    @Override
    public ContractDocument updateCurrentVersionOfContractDocument(@NotNull ContractDocument contractDocumentToUpdate)
    {
        return this.mongoOperations.save(contractDocumentToUpdate);
    }

    @Override
    public ContractDocument addOldVersionOfContractDocument(@NotNull ContractDocument oldVersionContractDocument)
    {
        return this.mongoOperations.insert(oldVersionContractDocument,OLD_VERSION);
    }

    @Override
    public Optional<ContractDocument> getCurrentVersionOfContractDocumentById(@NotNull ObjectId contractDocumentId)
    {
        ContractDocument contractDocument = this.mongoOperations.findById(contractDocumentId,ContractDocument.class);

        return Optional.ofNullable(contractDocument);
    }

    @Override
    public Optional<ContractDocument> getOldVersionOfContractDocumentById(@NotNull ObjectId contractDocumentId)
    {
        ContractDocument contractDocument = this.mongoOperations.findById(contractDocumentId,ContractDocument.class,OLD_VERSION);

        return Optional.ofNullable(contractDocument);
    }

    @Override
    public List<ContractDocument> getAllCurrentVersionOfContractDocumentByListOfId(@NotNull List<ObjectId> contractDocumentIds)
    {
        Criteria criteria = new Criteria("contractDocumentId");
        criteria.in(contractDocumentIds);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query, ContractDocument.class);
    }

    @Override
    public List<ContractDocument> getAllOldVersionOfContractDocumentByListOfId(@NotNull List<ObjectId> contractDocumentIds)
    {
        Criteria criteria = new Criteria("contractDocumentId");
        criteria.in(contractDocumentIds);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query, ContractDocument.class,OLD_VERSION);
    }

    @Override
    public List<ContractDocument> getAllCurrentVersionOfContractDocumentByInsuredId(@NotNull ObjectId insuredId)
    {
        Criteria criteria = new Criteria("insured");
        criteria.is(insuredId);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,ContractDocument.class);
    }

    @Override
    public List<ContractDocument> getAllCurrentVersionOfContractDocumentByProductId(@NotNull ObjectId productId)
    {
        Criteria criteria = new Criteria("product");
        criteria.is(productId);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,ContractDocument.class);
    }






}
