package sk.stu.fei.uim.bp.application.backend.client.service.implementation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stu.fei.uim.bp.application.backend.client.domain.*;
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientCompanyRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.ClientRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.PhysicalPersonRepository;
import sk.stu.fei.uim.bp.application.backend.client.repository.implementation.ClientRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientCompanyService;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.PhysicalPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.SelfEmployedPersonService;
import sk.stu.fei.uim.bp.application.backend.file.FileRepository;
import sk.stu.fei.uim.bp.application.backend.file.FileRepositoryImpl;
import sk.stu.fei.uim.bp.application.backend.file.FileWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService, ClientCompanyService, PhysicalPersonService, SelfEmployedPersonService
{
    private final ClientRepository clientRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final PhysicalPersonRepository physicalPersonRepository;
    private final FileRepository fileRepository;

    @Autowired
    public ClientServiceImpl(ClientRepositoryImpl clientRepositoryImpl, FileRepositoryImpl fileRepositoryImpl) {
        this.clientRepository = clientRepositoryImpl;
        this.clientCompanyRepository = clientRepositoryImpl;
        this.physicalPersonRepository = clientRepositoryImpl;
        this.fileRepository = fileRepositoryImpl;
    }


    @Override
    public List<Client> getAllClientsOfAgent(ObjectId agentId)
    {
        return this.clientRepository.getAllClientsOfCurrentAgent(agentId);
    }

    @Override
    public Optional<Client> getClientById(ObjectId clientId)
    {
        return this.clientRepository.getClientById(clientId);
    }

    @Override
    public List<Client> getClientByNameOrBySurnameOrByEmailOrByPersonalNumberOrByIcoOrByBusinessName(String search)
    {
        return this.clientRepository.getClientByNameOrBySurnameOrByEmailOrByPersonalNumberOrByIcoOrByBusinessName(search);
    }

    @Override
    public List<PhysicalPerson> getPhysicalPersonByNameOrBySurnameOrByEmailOrByPersonalNumber(String search)
    {
        return this.physicalPersonRepository.getPhysicalPersonByNameOrBySurnameOrByEmailOrByPersonalNumber(search);
    }

    @Override
    public ClientCompany addNewClientCompany(@NotNull ClientCompany newClientCompany)
    {
        ClientCompany added = (ClientCompany) this.clientRepository.addClient(newClientCompany);

        List<ObjectId> managers = added.getManagers();
        for (ObjectId manager : managers) {
            Optional<Client> client = this.clientRepository.getClientById(manager);
            if (client.isPresent()) {
                PhysicalPerson physicalPerson = (PhysicalPerson) client.get();
                this.physicalPersonRepository.addClientCompanyForPhysicalPerson(physicalPerson, added.getClientId());
            }
        }

        return added;
    }

    @Override
    public ClientCompany updateClientCompany(@NotNull ClientCompany upadateClientCompany)
    {
        Optional<Client> client = this.clientRepository.getClientById(upadateClientCompany.getClientId());
        ClientCompany clientBeforeUpdate = (ClientCompany) client.get();

        ClientCompany updatedClient = (ClientCompany) this.clientRepository.updateClient(upadateClientCompany);

        updateListOfClientCompaniesOfPhysicalPerson(clientBeforeUpdate.getManagers(),updatedClient.getManagers(),updatedClient.getClientId());

        return updatedClient;

    }


    private void updateListOfClientCompaniesOfPhysicalPerson(List<ObjectId> managersInBeforeUpdateClient,
                                                             List<ObjectId> managersInUpdatedClient, ObjectId clientId)
    {
        for(ObjectId manager:managersInBeforeUpdateClient)
        {
            if(!managersInUpdatedClient.contains(manager))
            {
                PhysicalPerson physicalPerson = (PhysicalPerson) this.clientRepository.getClientById(manager).get();
                this.physicalPersonRepository.removeClientCompanyFromPhysicalPerson(physicalPerson,clientId);
            }
        }

        for(ObjectId manager:managersInUpdatedClient)
        {
            if(!managersInBeforeUpdateClient.contains(manager))
            {
                PhysicalPerson physicalPerson = (PhysicalPerson) this.clientRepository.getClientById(manager).get();
                this.physicalPersonRepository.addClientCompanyForPhysicalPerson(physicalPerson,clientId);

            }
        }
    }

    @Override
    public PhysicalPerson addNewPhysicalPerson(@NotNull PhysicalPerson newPhysicalPerson,
                                               @NotNull FileWrapper frontSideOfIdentityCard,
                                               @NotNull FileWrapper backSideOfIdentityCard) {


        return (PhysicalPerson) addNewPerson(newPhysicalPerson,frontSideOfIdentityCard,backSideOfIdentityCard);
    }

    @Override
    public PhysicalPerson addNewPhysicalPerson(@NotNull PhysicalPerson newPhysicalPerson)
    {
        return (PhysicalPerson) addNewPerson(newPhysicalPerson);
    }

    @Override
    public PhysicalPerson updatePhysicalPerson(@NotNull PhysicalPerson updatePhysicalPerson)
    {
        return (PhysicalPerson) updatePerson(updatePhysicalPerson);
    }

    @Override
    public PhysicalPerson updatePhysicalPerson(@NotNull PhysicalPerson updatePhysicalPerson,
                                               @NotNull FileWrapper frontSideOfIdentityCard,
                                               @NotNull FileWrapper backSideOfIdentityCard )
    {

        return (PhysicalPerson) updatePerson(updatePhysicalPerson,frontSideOfIdentityCard,backSideOfIdentityCard);

    }

    @Override
    public SelfEmployedPerson addNewSelfEmployedPerson(@NotNull SelfEmployedPerson newSelfEmployedPerson,
                                               @NotNull FileWrapper frontSideOfIdentityCard,
                                               @NotNull FileWrapper backSideOfIdentityCard) {

        return (SelfEmployedPerson) addNewPerson(newSelfEmployedPerson,frontSideOfIdentityCard,backSideOfIdentityCard);
    }

    @Override
    public SelfEmployedPerson addNewSelfEmployedPerson(@NotNull SelfEmployedPerson newSelfEmployedPerson)
    {
        return (SelfEmployedPerson) addNewPerson(newSelfEmployedPerson);
    }

    @Override
    public SelfEmployedPerson updateSelfEmplyedPerson(@NotNull SelfEmployedPerson updateSelfEmployedPerson,
                                                      @NotNull FileWrapper frontSideOfIdentityCard,
                                                      @NotNull FileWrapper backSideOfIdentityCard)
    {
        return (SelfEmployedPerson) updatePerson(updateSelfEmployedPerson,frontSideOfIdentityCard,backSideOfIdentityCard);
    }

    @Override
    public SelfEmployedPerson updateSelfEmplyedPerson(@NotNull SelfEmployedPerson updateSelfEmployedPerson)
    {
        return (SelfEmployedPerson) updatePerson(updateSelfEmployedPerson);
    }



    private Person addNewPerson(Person newPerson, FileWrapper frontSideOfIdentityCard,FileWrapper backSideOfIdentityCard)
    {
        IdentifyCardCopyReference identifyCardCopyReference = getIdentifyCardCopyReference(newPerson,
                frontSideOfIdentityCard,backSideOfIdentityCard);

        newPerson.setIdentifyCardCopy(identifyCardCopyReference);

        return (Person) this.clientRepository.addClient(newPerson);
    }

    private Person addNewPerson(Person newPerson)
    {
        return (Person) this.clientRepository.addClient(newPerson);
    }


    private Person updatePerson(Person updatePerson, FileWrapper frontSideOfIdentityCard, FileWrapper backSideOfIdentityCard)
    {
        if(updatePerson.getIdentifyCardCopy() != null)
        {
            deleteCopyIdentityCardOfPerson(updatePerson.getIdentifyCardCopy());
        }

        IdentifyCardCopyReference identifyCardCopyReference = getIdentifyCardCopyReference(updatePerson,
                frontSideOfIdentityCard,backSideOfIdentityCard);

        updatePerson.setIdentifyCardCopy(identifyCardCopyReference);

        return (Person) this.clientRepository.updateClient(updatePerson);
    }

    private Person updatePerson(Person updatePerson)
    {
        return (Person) this.clientRepository.updateClient(updatePerson);
    }


    private IdentifyCardCopyReference getIdentifyCardCopyReference(Person person,
                                                                   FileWrapper frontSideOfIdentityCard,
                                                                   FileWrapper backSideOfIdentityCard)
    {
        ObjectId frontSideId = uploadFileOfIdentityCard(person,frontSideOfIdentityCard,true);
        ObjectId backSideId = uploadFileOfIdentityCard(person,backSideOfIdentityCard,false);

        IdentifyCardCopyReference identifyCardCopyReference = new IdentifyCardCopyReference();
        identifyCardCopyReference.setFrontSide(frontSideId);
        identifyCardCopyReference.setBackSide(backSideId);

        return identifyCardCopyReference;
    }

    private ObjectId uploadFileOfIdentityCard(Person person, FileWrapper file, boolean isFront)
    {
        String front = "predna_strana";
        String back = "zadna_strana";
        String start = "OP";

        DBObject metaData = new BasicDBObject();
        metaData.put("uploadName",file.getFileData().getFileName());

        if(file.isSetCustomFileName())
        {
            return this.fileRepository.saveFile(file.getFile(),file.getFileData(),file.getCustomFileName(),metaData);
        }

        String name;
        if(isFront)
        {
            name = start + "_" + person.getFirstName() + "_" + person.getSurname() + "_" + front;
        }
        else
            name = start + "_" + person.getFirstName() + "_" + person.getSurname() + "_" + back;

        return this.fileRepository.saveFile(file.getFile(),file.getFileData(),name,metaData);

    }

    private void deleteCopyIdentityCardOfPerson(IdentifyCardCopyReference identifyCardCopyReference)
    {
        this.fileRepository.deleteFile(identifyCardCopyReference.getFrontSide());
        this.fileRepository.deleteFile(identifyCardCopyReference.getBackSide());
    }

    @Override
    public IdentifyCardCopyFiles getIdentifyCardOfPerson(IdentifyCardCopyReference identifyCardCopyReference)
    {
        IdentifyCardCopyFiles card = new IdentifyCardCopyFiles();
        card.setFrontSide(this.fileRepository.getInformationAboutFile(identifyCardCopyReference.getFrontSide()));
        card.setBackSide(this.fileRepository.getInformationAboutFile(identifyCardCopyReference.getBackSide()));

        return card;

    }

    @Override
    public Optional<InputStream> getCardSideFile(ObjectId cardSide)
    {
        Optional<InputStream> file;
        try {
            file = Optional.of(this.fileRepository.getFile(cardSide));
        } catch (IOException e) {
            file = Optional.empty();
        }

        return file;
    }






}
