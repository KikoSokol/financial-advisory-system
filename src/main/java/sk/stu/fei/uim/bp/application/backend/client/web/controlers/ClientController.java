package sk.stu.fei.uim.bp.application.backend.client.web.controlers;

import com.vaadin.flow.data.provider.ListDataProvider;
import org.bson.types.ObjectId;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.service.ClientService;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.ClientMainView;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ClientController extends MainClientController
{
    private final ClientService clientService;

    List<TableClientItem> allClients;
    private final ListDataProvider<TableClientItem> clientDataProvider;


    public ClientController(ClientMainView clientMainView,ObjectId currentAgentId, ClientServiceImpl clientService)
    {
        super(clientMainView,currentAgentId);
        this.clientService = clientService;

        this.allClients = loadDataToTable();

        this.clientDataProvider = new ListDataProvider<>(allClients);

        this.clientMainView.getClientTable().setDataProvider(this.clientDataProvider);


    }

    public void refreshTable()
    {
        this.allClients.clear();
        this.allClients.addAll(loadDataToTable());
        this.clientDataProvider.refreshAll();
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
            dataForTable.add(new TableClientItem(client));
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


}
