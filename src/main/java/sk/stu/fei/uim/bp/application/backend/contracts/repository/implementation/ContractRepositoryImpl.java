package sk.stu.fei.uim.bp.application.backend.contracts.repository.implementation;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.Contract;
import sk.stu.fei.uim.bp.application.backend.contracts.repository.ContractRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class ContractRepositoryImpl implements ContractRepository
{
    private final MongoOperations mongoOperations;

    @Autowired
    public ContractRepositoryImpl(MongoTemplate mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Contract addNewContract(@NotNull Contract newContract)
    {
        return this.mongoOperations.insert(newContract);
    }

    @Override
    public boolean deleteContract(@NotNull Contract contractToDelete)
    {
        DeleteResult deleteResult = this.mongoOperations.remove(contractToDelete);

        return deleteResult.wasAcknowledged();
    }

    @Override
    public Contract updateContract(@NotNull Contract contractToUpdate)
    {
        return this.mongoOperations.save(contractToUpdate);
    }

    @Override
    public Optional<Contract> getContractById(@NotNull ObjectId contractId)
    {
        Contract contract = this.mongoOperations.findById(contractId,Contract.class);

        return Optional.ofNullable(contract);
    }

    @Override
    public List<Contract> getAllContractsOfCurrentAgent(@NotNull ObjectId currentAgentId)
    {
        Criteria criteria = new Criteria("agent");
        criteria.is(currentAgentId);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query,Contract.class);
    }

    @Override
    public List<Contract> getAllContractsByListOfId(@NotNull List<ObjectId> contractsId)
    {
        Criteria criteria = new Criteria("contractId");
        criteria.in(contractsId);

        Query query = new Query(criteria);

        return this.mongoOperations.find(query, Contract.class);
    }

    @Override
    public Optional<Contract> getContractByContractNumber(@NotNull String contractNumber)
    {
        Criteria criteria = new Criteria("contractNumber");
        criteria.is(contractNumber);

        Query query = new Query(criteria);

        Contract contract = this.mongoOperations.findOne(query,Contract.class);

        return Optional.ofNullable(contract);
    }

}
