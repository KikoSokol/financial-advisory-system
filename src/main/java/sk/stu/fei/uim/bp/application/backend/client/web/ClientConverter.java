package sk.stu.fei.uim.bp.application.backend.client.web;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientCompanyService;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.PhysicalPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.SelfEmployedPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;

import java.util.LinkedList;
import java.util.List;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClientConverter
{
    private final ClientService clientService;
    private final ClientCompanyService clientCompanyService;
    private final PhysicalPersonService physicalPersonService;
    private final SelfEmployedPersonService selfEmployedPersonService;


    @Autowired
    public  ClientConverter(ClientServiceImpl clientService)
    {
        this.clientService = clientService;
        this.clientCompanyService = clientService;
        this.physicalPersonService = clientService;
        this.selfEmployedPersonService = clientService;
    }


    public List<TableClientItem> convertListClientsToListOfTableClientItem(List<Client> clients)
    {
        List<TableClientItem> tableClientItems = new LinkedList<>();

        for(Client client : clients)
        {
            tableClientItems.add(createTableClientItem(client));
        }

        return tableClientItems;
    }


    public ClientDto convertClientToClientDto(Client client)
    {
        if(client instanceof ClientCompany)
        {
            ClientCompany clientCompany = (ClientCompany) client;
            List<PhysicalPersonDto> managers = getManagersDto(clientCompany.getManagers());
            return new ClientCompanyDto(clientCompany,managers);
        }
        else if(client instanceof PhysicalPerson)
        {
            return new PhysicalPersonDto((PhysicalPerson) client);
        }
        else
        {
            return new SelfEmployedPersonDto((SelfEmployedPerson) client);
        }
    }






    private TableClientItem createTableClientItem(Client client)
    {
        if(client instanceof ClientCompany)
        {
            return createTableClientCompanyItem((ClientCompany) client);
        }
        else if(client instanceof PhysicalPerson)
        {
            return createTablePhysicalPersonItem((PhysicalPerson) client);
        }
        else
        {
            return createTableSelfEmployedPersonItem((SelfEmployedPerson) client);
        }
    }

    private TableClientItem createTableClientCompanyItem(ClientCompany clientCompany)
    {
        List<PhysicalPersonDto> managersOfCompany = getManagersDto(clientCompany.getManagers());

        ClientCompanyDto clientCompanyDto = new ClientCompanyDto(clientCompany,managersOfCompany);

        return new TableClientItem(clientCompanyDto);
    }

    private List<PhysicalPersonDto> getManagersDto(List<ObjectId> managers)
    {
        List<PhysicalPerson> physicalPeople = this.physicalPersonService.getAllPhysicalPersonsByListOfIds(managers);

        List<PhysicalPersonDto> dtos = new LinkedList<>();

        for(PhysicalPerson physicalPerson : physicalPeople)
        {
            dtos.add(new PhysicalPersonDto(physicalPerson));
        }

        return dtos;
    }

    private TableClientItem createTablePhysicalPersonItem(PhysicalPerson physicalPerson)
    {

        PhysicalPersonDto physicalPersonDto = new PhysicalPersonDto(physicalPerson);

        return new TableClientItem(physicalPersonDto);
    }

    private TableClientItem createTableSelfEmployedPersonItem(SelfEmployedPerson selfEmployedPerson)
    {
        SelfEmployedPersonDto selfEmployedPersonDto = new SelfEmployedPersonDto(selfEmployedPerson);

        return new TableClientItem(selfEmployedPersonDto);

    }


}
