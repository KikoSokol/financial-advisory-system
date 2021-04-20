package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.PhysicalPersonService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.ClientCompanyDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.PhysicalPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.dto.SelfEmployedPersonDto;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientFilter;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ClientController extends MainClientController
{
    private final ClientService clientService;
    private final PhysicalPersonService physicalPersonService;

    List<TableClientItem> allClients;
    private final ListDataProvider<TableClientItem> clientDataProvider;
    private final TableClientFilter tableFilter;


    public ClientController(ClientMainView clientMainView,ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.clientService = clientService;
        this.physicalPersonService = clientService;

        this.allClients = loadDataToTable();

        this.clientDataProvider = new ListDataProvider<>(allClients);

        this.clientMainView.getClientTable().setDataProvider(this.clientDataProvider);

        this.tableFilter = new TableClientFilter();

        addFiterToClientDataProvider();

    }

    public void refreshTable()
    {
        this.allClients.clear();
        this.allClients.addAll(loadDataToTable());
        this.clientDataProvider.refreshAll();
    }

    public void filterDataInTable()
    {
        this.clientDataProvider.refreshAll();
    }

    public void  addFiterToClientDataProvider()
    {
        this.clientDataProvider.setFilter(tableClientItem -> this.tableFilter.test(tableClientItem));
    }


    public List<Client> getAllClientFromDatabase()
    {
        return this.clientService.getAllClientsOfAgent(currentAgentId);
    }

    public List<TableClientItem> convertClientsToDataForTable(List<Client> clients)
    {
        List<TableClientItem> dataForTable = new LinkedList<>();

        for(Client client : clients)
        {
            dataForTable.add(createTableClientItem(client));
        }

        return dataForTable;
    }

    public List<TableClientItem> loadDataToTable()
    {
        return convertClientsToDataForTable(getAllClientFromDatabase());
    }

    public Optional<Client> getClientById(ObjectId clientId)
    {
        return this.clientService.getClientById(clientId);
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

    public TableClientFilter getTableFilter()
    {
        return this.tableFilter;
    }




}
