package sk.stu.fei.uim.bp.application.backend.client.repository.implementation;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientCompanyRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.PhysicalPersonRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository, ClientCompanyRepository, PhysicalPersonRepository
{
    MongoOperations mongoOperations;

    @Autowired
    public ClientRepositoryImpl(MongoTemplate mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Client addClient(@NotNull Client newClient)
    {
        return mongoOperations.insert(newClient);
    }

    @Override
    public void deleteClient(@NotNull Client deleteClient)
    {
        mongoOperations.remove(deleteClient);
    }

    @Override
    public Client updateClient(@NotNull Client updateClient)
    {
        return mongoOperations.save(updateClient);
    }

    @Override
    public Optional<Client> getClientById(@NotNull ObjectId clientId)
    {
        Criteria criteria = new Criteria("clientId");
        criteria.is(clientId);

        Query query = new Query(criteria);

        Client client = mongoOperations.findOne(query,Client.class);

        return Optional.ofNullable(client);
    }

    @Override
    public List<Client> getAllClientsOfCurrentAgent(@NotNull ObjectId currentAgentId)
    {
        Criteria criteria = new Criteria("agent");
        criteria.is(currentAgentId);

        Query query = new Query(criteria);

        return mongoOperations.find(query,Client.class);
    }

    @Override
    public Client addContractForClient(@NotNull Client client,@NotNull ObjectId contractToAdd)
    {
        Update update = new Update();
        update.addToSet("contracts",contractToAdd);

        return updateContractsOfClient(client,update);

    }

    @Override
    public Client removeContractFromClient(@NotNull Client client,@NotNull ObjectId contractToRemove)
    {
        Update update = new Update();
        update.pull("contracts",contractToRemove);

        return updateContractsOfClient(client,update);
    }

    private Client updateContractsOfClient(Client client, Update update)
    {
        Criteria criteria = new Criteria("clientId");
        criteria.is(client.getClientId());

        Query query = new Query(criteria);

        return mongoOperations.update(Client.class)
                .matching(query)
                .apply(update)
                .withOptions(FindAndModifyOptions.options().returnNew(true))
                .findAndModifyValue();
    }

    @Override
    public PhysicalPerson addClientCompanyForPhysicalPerson(@NotNull PhysicalPerson physicalPerson,@NotNull ObjectId clientCompanyToAdd)
    {
        Update update = new Update();
        update.addToSet("clientCompanies",clientCompanyToAdd);

        return updateClientCompaniesOfPhysicalPerson(physicalPerson,update);
    }

    @Override
    public PhysicalPerson removeClientCompanyFromPhysicalPerson(@NotNull PhysicalPerson physicalPerson,@NotNull ObjectId clientCompanyToRemove)
    {
        Update update = new Update();
        update.pull("clientCompanies",clientCompanyToRemove);

        return updateClientCompaniesOfPhysicalPerson(physicalPerson,update);

    }

    private PhysicalPerson updateClientCompaniesOfPhysicalPerson(PhysicalPerson physicalPerson, Update update)
    {
        Criteria criteria = new Criteria("clientId");
        criteria.is(physicalPerson.getClientId());

        Query query = new Query(criteria);

        return (PhysicalPerson) mongoOperations.update(Client.class)
                .matching(query)
                .apply(update)
                .withOptions(FindAndModifyOptions.options().returnNew(true))
                .findAndModifyValue();
    }

    @Override
    public ClientCompany addManagerToClientCompany(@NotNull ClientCompany clientCompany, @NotNull ObjectId physicalPerson)
    {
        Update update = new Update();
        update.addToSet("managers",physicalPerson);

        return updateManagersOfClientCompany(clientCompany,update);
    }

    @Override
    public ClientCompany removeManagerFromClientCompany(@NotNull ClientCompany clientCompany, @NotNull ObjectId physicalPerson)
    {
        Update update = new Update();
        update.pull("managers",physicalPerson);

        return updateManagersOfClientCompany(clientCompany,update);
    }

    private ClientCompany updateManagersOfClientCompany(ClientCompany clientCompany, Update update)
    {
        Criteria criteria = new Criteria("clientId");
        criteria.is(clientCompany.getClientId());

        Query query = new Query(criteria);

        return (ClientCompany) mongoOperations.update(Client.class)
                .matching(query)
                .apply(update)
                .withOptions(FindAndModifyOptions.options().returnNew(true))
                .findAndModifyValue();
    }
    
    








}
