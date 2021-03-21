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

public class ClientController
{
    private final ClientService clientService;
    private final ObjectId currentAgentId = new ObjectId("601b6300dbf3207494372a20");
    private final ListDataProvider<TableClientItem> clientDataProvider;



    private ClientMainView clientMainView;

    public ClientController(ClientServiceImpl clientService)
    {
        this.clientService = clientService;

        this.clientDataProvider = new ListDataProvider<>(getDataForTable());

    }

    public void init(ClientMainView clientMainView)
    {
        this.clientMainView = clientMainView;
        this.clientMainView.getClientTable().setDataProvider(this.clientDataProvider);

    }

    public List<Client> getAllClient()
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

    public List<TableClientItem> getDataForTable()
    {
        return convertClientsToDataForTable(getAllClient());
    }


    private void updateTable()
    {
        this.clientDataProvider.refreshAll();
    }


}
