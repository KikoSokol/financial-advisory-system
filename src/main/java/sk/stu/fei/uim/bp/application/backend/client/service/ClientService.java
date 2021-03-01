package sk.stu.fei.uim.bp.application.backend.client.service;

import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.IdentifyCardCopyFiles;
import sk.stu.fei.uim.bp.application.backend.client.domain.IdentifyCardCopyReference;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ClientService
{

    List<Client> getAllClientsOfAgent(ObjectId agentId);

    Optional<Client> getClientById(ObjectId clientId);

    List<Client> getClientByNameOrBySurnameOrByEmailOrByPersonalNumberOrByIcoOrByBusinessName(String search);

    IdentifyCardCopyFiles getIdentifyCardOfPerson(IdentifyCardCopyReference identifyCardCopyReference);

    Optional<InputStream> getCardSideFile(ObjectId cardSide);
}
