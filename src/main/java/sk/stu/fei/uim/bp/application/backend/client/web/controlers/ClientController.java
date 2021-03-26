package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientFilter;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ClientController extends MainClientController
{
    private final ClientService clientService;

    List<TableClientItem> allClients;
    private final ListDataProvider<TableClientItem> clientDataProvider;
    private final TableClientFilter tableFilter;


    public ClientController(ClientMainView clientMainView,ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.clientService = clientService;

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
        TableClientItem tableItem = new TableClientItem(clientCompany.getClientId());
        tableItem.setName(clientCompany.getBusinessName());
        tableItem.setIco(clientCompany.getIco());

        Optional<Client> managerOptional = this.clientService.getClientById(clientCompany.getManagers().get(0));
        PhysicalPerson manager = (PhysicalPerson) managerOptional.get();

        tableItem.setPhone(manager.getPhone());
        tableItem.setEmail(manager.getEmail());

        return tableItem;


    }

    private TableClientItem createTablePhysicalPersonItem(PhysicalPerson physicalPerson)
    {
        TableClientItem tableItem = new TableClientItem(physicalPerson.getClientId());
        tableItem.setName(physicalPerson.getFirstName());
        tableItem.setSurname(physicalPerson.getSurname());
        tableItem.setEmail(physicalPerson.getEmail());
        tableItem.setPhone(physicalPerson.getPhone());
        tableItem.setPersonalNumber(physicalPerson.getPersonalNumber());

        return tableItem;
    }

    private TableClientItem createTableSelfEmployedPersonItem(SelfEmployedPerson selfEmployedPerson)
    {
        TableClientItem tableItem = new TableClientItem(selfEmployedPerson.getClientId());
        tableItem.setName(selfEmployedPerson.getFirstName());
        tableItem.setSurname(selfEmployedPerson.getSurname());
        tableItem.setEmail(selfEmployedPerson.getEmail());
        tableItem.setPhone(selfEmployedPerson.getPhone());
        tableItem.setPersonalNumber(selfEmployedPerson.getPersonalNumber());
        tableItem.setIco(selfEmployedPerson.getIco());

        return tableItem;

    }

    public TableClientFilter getTableFilter()
    {
        return this.tableFilter;
    }




}
