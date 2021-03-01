package sk.stu.fei.uim.bp.application.backend.client.repository;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;

import java.util.List;
import java.util.Optional;

public interface ClientRepository
{
    Client addClient(Client newClient);

    void deleteClient(Client deleteClient);

    Client updateClient(Client updateClient);

    Optional<Client> getClientById(ObjectId clientId);

    List<Client> getAllClientsOfCurrentAgent(ObjectId currentAgentId);

    Client addContractForClient(Client client, ObjectId contractToAdd);

    Client removeContractFromClient(Client client, ObjectId contractToRemove);

    List<Client> getClientByNameOrBySurnameOrByEmailOrByPersonalNumberOrByIcoOrByBusinessName(String search);











}
