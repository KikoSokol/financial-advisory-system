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
import sk.stu.fei.uim.bp.application.backend.client.web.editors.CompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.PhysicalPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.events.clientCompanyEvents.ClientCompanySaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.phycicalPersonEvents.PhysicalPersonSaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.events.selfEmployedPersonEvents.SelfEmployedPersonSaveEvent;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;

import java.util.LinkedList;
import java.util.List;

public class ClientController
{
    private final ClientService clientService;
    private final ObjectId currentAgentId = new ObjectId("601b6300dbf3207494372a20");
    private final ListDataProvider<TableClientItem> clientDataProvider;

    private PhysicalPersonController physicalPersonController;
    private SelfEmployedPersonController selfEmployedPersonController;
    private ClientCompanyController clientCompanyController;


    private ClientMainView clientMainView;

    public ClientController(ClientServiceImpl clientService)
    {
        this.clientService = clientService;
        this.physicalPersonController = new PhysicalPersonController(clientService);
        this.selfEmployedPersonController = new SelfEmployedPersonController(clientService);
        this.clientCompanyController = new ClientCompanyController(clientService);

        this.clientDataProvider = new ListDataProvider<>(getDataForTable());



    }

    public void init(ClientMainView clientMainView)
    {
        this.clientMainView = clientMainView;
        this.clientMainView.getClientTable().setDataProvider(this.clientDataProvider);
        setActionForEditors();
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


    public void addNewPhysicalPerson()
    {
        PhysicalPersonEditor editor = this.clientMainView.getPhysicalPersonEditor();
        editor.setPhysicalPersonDto(this.physicalPersonController.addNewPhysicalPerson(this.currentAgentId));
        this.clientMainView.showMainWindow(editor);
    }

    public void addNewSelfEmployedPerson()
    {
        SelfEmployedPersonEditor editor = this.clientMainView.getSelfEmployedPersonEditor();
        editor.setSelfEmployedPersonDto(this.selfEmployedPersonController.addNewSelfEmployedPerson(this.currentAgentId));
        this.clientMainView.showMainWindow(editor);
    }

    public void addNewClientCompany()
    {
        CompanyEditor editor = this.clientMainView.getCompanyEditor();
        editor.setClientCompanyDto(this.clientCompanyController.addNewClientCompany(this.currentAgentId));
        this.clientMainView.showMainWindow(editor);
    }


    private void setActionForEditors()
    {
        PhysicalPersonEditor physicalPersonEditor = this.clientMainView.getPhysicalPersonEditor();
        physicalPersonEditor.addListener(PhysicalPersonSaveEvent.class,this::saveNewPhysicalPerson);


        SelfEmployedPersonEditor selfEmployedPersonEditor = this.clientMainView.getSelfEmployedPersonEditor();
        selfEmployedPersonEditor.addListener(SelfEmployedPersonSaveEvent.class,this::saveNewSelfEmployedPerson);

        CompanyEditor companyEditor = this.clientMainView.getCompanyEditor();
        companyEditor.addListener(ClientCompanySaveEvent.class,this::saveNewClientCompany);



    }

    private void saveNewPhysicalPerson(PhysicalPersonSaveEvent event)
    {
        try {
            PhysicalPerson p = this.physicalPersonController.doSaveNewPhysicalPerson(event.getPhysicalPersonDto());
            System.out.println(p);
            this.clientMainView.getPhysicalPersonEditor().clear();
            this.clientMainView.closeMainWindow();
            this.physicalPersonController.clear();

        } catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať FO (ClientController)");
            exception.printStackTrace();
        }
    }

    private void saveNewSelfEmployedPerson(SelfEmployedPersonSaveEvent event)
    {
        try {
            SelfEmployedPerson p = this.selfEmployedPersonController.doSaveNewSelfEmployedPerson(event.getSelfEmployedPersonDto());
            System.out.println(p);
            this.clientMainView.getSelfEmployedPersonEditor().clear();
            this.clientMainView.closeMainWindow();
            this.selfEmployedPersonController.clear();
        }catch (Exception exception)
        {
            System.out.println("Nepodarilo sa pridať Živnostnika (ClientController)");
            exception.printStackTrace();
        }
    }

    private void saveNewClientCompany(ClientCompanySaveEvent event)
    {
        try {
            ClientCompany clientCompany = this.clientCompanyController.doSaveNewClientCompany(event.getClientCompanyDto());
            System.out.println(clientCompany);
            this.clientMainView.getCompanyEditor().clear();
            this.clientMainView.closeMainWindow();
            this.clientCompanyController.clear();
        } catch (Exception exception) {
            System.out.println("Nepodarilo sa pridať spoločnosť (ClientController)");
        }
    }








    private void updateTable()
    {
        this.clientDataProvider.refreshAll();
    }


}
