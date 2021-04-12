package sk.stu.fei.uim.bp.application.backend.client.web;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import sk.stu.fei.uim.bp.application.MainView;
import sk.stu.fei.uim.bp.application.backend.client.domain.Client;
import sk.stu.fei.uim.bp.application.backend.client.domain.ClientCompany;
import sk.stu.fei.uim.bp.application.backend.client.domain.PhysicalPerson;
import sk.stu.fei.uim.bp.application.backend.client.domain.SelfEmployedPerson;
import sk.stu.fei.uim.bp.application.backend.client.service.implementation.ClientServiceImpl;
import sk.stu.fei.uim.bp.application.backend.client.web.components.ClientWindow;
import sk.stu.fei.uim.bp.application.backend.client.web.controlers.*;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.ClientCompanyEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.PhysicalPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.editors.SelfEmployedPersonEditor;
import sk.stu.fei.uim.bp.application.backend.client.web.table.TableClientItem;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;

import java.util.Optional;


/**
 * A Designer generated component for the client-main-view template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("client-main-view")
@JsModule("./views/client/client-main-view.js")
@PageTitle(value = "Klienti")
@Route(value = "klienti", layout = MainView.class)
public class ClientMainView extends PolymerTemplate<ClientMainView.ClientMainViewModel> {


    @Id("addPhysicalPersonButton")
    private Button addPhysicalPersonButton;

    @Id("addSelfEmployedPersonButton")
    private Button addSelfEmployedPersonButton;

    @Id("addClientCompanyButton")
    private Button addClientCompanyButton;

    @Id("clientTable")
    private Grid<TableClientItem> clientTable;

    private final HeaderRow headerRow;

    private Dialog mainWindow;

    private ClientWindow clientWindow;

    private ClientCompanyEditor clientCompanyEditor;

    private PhysicalPersonEditor physicalPersonEditor;

    private SelfEmployedPersonEditor selfEmployedPersonEditor;


    private final ObjectId currentAgentId = new ObjectId("601b6300dbf3207494372a20");

    private final ClientController clientController;

    private final PhysicalPersonController physicalPersonController;
    private final SelfEmployedPersonController selfEmployedPersonController;
    private final ClientCompanyController clientCompanyController;



    @Autowired
    public ClientMainView(ClientServiceImpl clientService)
    {

        this.clientController = new ClientController(this,this.currentAgentId,clientService);

        initMainWindow();
//        initClientWindow();
        initPhysicalPersonEditor();
        initSelfEmployedPersonEditor();
        initCompanyEditor(clientService);


        this.physicalPersonController = new PhysicalPersonController(this,this.currentAgentId,clientService);
        this.selfEmployedPersonController = new SelfEmployedPersonController(this,this.currentAgentId,clientService);
        this.clientCompanyController = new ClientCompanyController(this,this.currentAgentId,clientService);

//        this.mainWindow.add(this.clientWindow);


        setActionOnAddButton();

        initColumns();
        headerRow = clientTable.appendHeaderRow();
        initSearchingRow();

        this.clientTable.addItemDoubleClickListener(item ->{
           TableClientItem selectedClientItem = item.getItem();
           this.doUpdateClient(selectedClientItem.getId());
        });

    }

    public void refreshTable()
    {
        this.clientController.refreshTable();
    }


    private void setActionOnAddButton()
    {
        this.addPhysicalPersonButton.addClickListener(event -> this.physicalPersonController.addNewPhysicalPerson());

        this.addSelfEmployedPersonButton.addClickListener(event -> this.selfEmployedPersonController.addNewSelfEmployedPerson());

        this.addClientCompanyButton.addClickListener(event -> this.clientCompanyController.addNewClientCompany());
    }

    private void doUpdateClient(ObjectId clientId)
    {
        Optional<Client> clientOptional = this.clientController.getClientById(clientId);

        if(clientOptional.isEmpty())
        {
            showErrorMessage("Klienta sa nepodarilo načítať");
            return;
        }

        Client client = clientOptional.get();

        if(client instanceof PhysicalPerson)
            this.physicalPersonController.updatePhysicalPerson((PhysicalPerson) client);

        if(client instanceof SelfEmployedPerson)
            this.selfEmployedPersonController.updateSelfEmployedPerson((SelfEmployedPerson) client);

        if(client instanceof ClientCompany)
            this.clientCompanyController.updateClientCompany((ClientCompany) client);

    }

    public void resetMainWindow()
    {
        this.mainWindow.removeAll();
//        this.mainWindow.add(this.clientWindow);
    }

    public void showMainWindow(Component component)
    {
        this.mainWindow.add(component);
        this.mainWindow.setModal(false);
        this.mainWindow.open();
    }

    public void closeMainWindow()
    {
        this.mainWindow.removeAll();
        this.mainWindow.close();
    }


    private void initMainWindow()
    {
        this.mainWindow = new Dialog();
    }

//    private void initClientWindow()
//    {
//        this.clientWindow = new ClientWindow();
//    }

    private void initPhysicalPersonEditor()
    {
        this.physicalPersonEditor = new PhysicalPersonEditor();
    }

    private void initSelfEmployedPersonEditor()
    {
        this.selfEmployedPersonEditor = new SelfEmployedPersonEditor();
    }

    private void initCompanyEditor(ClientServiceImpl clientService)
    {
        this.clientCompanyEditor = new ClientCompanyEditor(clientService);
    }

    public PhysicalPersonEditor getPhysicalPersonEditor() {
        return physicalPersonEditor;
    }

    public SelfEmployedPersonEditor getSelfEmployedPersonEditor()
    {
        return this.selfEmployedPersonEditor;
    }

    public ClientCompanyEditor getCompanyEditor(){
        return this.clientCompanyEditor;
    }

    private void initSearchingRow()
    {
        initNameSearch();
        initSurnameSearch();
        initEmailSearch();
        initPhoneSearch();
        initPersonalNumberSearch();
        initIcoSearch();
    }

    private void initColumns()
    {
        Grid.Column<TableClientItem> nameColumn = clientTable.addColumn(TableClientItem::getName);
        nameColumn.setAutoWidth(true);
        nameColumn.setHeader("Meno");
        nameColumn.setKey("nameColumn");
        nameColumn.setId("nameColumn");

        Grid.Column<TableClientItem> surnameColumn = clientTable.addColumn(TableClientItem::getSurname);
        surnameColumn.setAutoWidth(true);
        surnameColumn.setHeader("Priezvisko");
        surnameColumn.setKey("surnameColumn");
        surnameColumn.setId("surnameColumn");

        Grid.Column<TableClientItem> emailColumn = clientTable.addColumn(TableClientItem::getEmail);
        emailColumn.setAutoWidth(true);
        emailColumn.setHeader("Email");
        emailColumn.setKey("emailColumn");
        emailColumn.setId("emailColumn");

        Grid.Column<TableClientItem> phoneColumn = clientTable.addColumn(TableClientItem::getPhone);
        phoneColumn.setAutoWidth(true);
        phoneColumn.setHeader("Telefón");
        phoneColumn.setKey("phoneColumn");
        phoneColumn.setId("phoneColumn");

        Grid.Column<TableClientItem> personalNumberColumn = clientTable.addColumn(TableClientItem::getPersonalNumber);
        personalNumberColumn.setAutoWidth(true);
        personalNumberColumn.setHeader("Rodné číslo");
        personalNumberColumn.setKey("personalNumberColumn");
        personalNumberColumn.setId("personalNumberColumn");

        Grid.Column<TableClientItem> icoColumn = clientTable.addColumn(TableClientItem::getIco);
        icoColumn.setAutoWidth(true);
        icoColumn.setHeader("IČO");
        icoColumn.setKey("icoColumn");
        icoColumn.setId("icoColumn");



    }

    private void initNameSearch()
    {
        Grid.Column<TableClientItem> column = clientTable.getColumnByKey("nameColumn");

        TextField nameSearch = new TextField();
        nameSearch.addValueChangeListener(event -> {
            this.clientController.getTableFilter().setName(event.getValue());
            this.clientController.filterDataInTable();
        });
        nameSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(nameSearch);
        nameSearch.setSizeFull();
        nameSearch.setPlaceholder("Filter");
    }

    private void initSurnameSearch()
    {
        Grid.Column<TableClientItem> column = clientTable.getColumnByKey("surnameColumn");

        TextField surnameSearch = new TextField();
        surnameSearch.addValueChangeListener(event -> {
            this.clientController.getTableFilter().setSurname(event.getValue());
            this.clientController.filterDataInTable();
        });
        surnameSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(surnameSearch);
        surnameSearch.setSizeFull();
        surnameSearch.setPlaceholder("Filter");
    }

    private void initEmailSearch()
    {
        Grid.Column<TableClientItem> column = clientTable.getColumnByKey("emailColumn");

        TextField emailSearch = new TextField();
        emailSearch.addValueChangeListener(event -> {
            this.clientController.getTableFilter().setEmail(event.getValue());
            this.clientController.filterDataInTable();
        });
        emailSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(emailSearch);
        emailSearch.setSizeFull();
        emailSearch.setPlaceholder("Filter");
    }

    private void initPhoneSearch()
    {
        Grid.Column<TableClientItem> column = clientTable.getColumnByKey("phoneColumn");

        TextField phoneSearch = new TextField();
        phoneSearch.addValueChangeListener(event -> {
            this.clientController.getTableFilter().setPhone(event.getValue());
            this.clientController.filterDataInTable();
        });
        phoneSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(phoneSearch);
        phoneSearch.setSizeFull();
        phoneSearch.setPlaceholder("Filter");
    }

    private void initPersonalNumberSearch()
    {
        Grid.Column<TableClientItem> column = clientTable.getColumnByKey("personalNumberColumn");

        TextField personalNumberSearch = new TextField();
        personalNumberSearch.addValueChangeListener(event -> {
            this.clientController.getTableFilter().setPersonalNumber(event.getValue());
            this.clientController.filterDataInTable();
        });
        personalNumberSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(personalNumberSearch);
        personalNumberSearch.setSizeFull();
        personalNumberSearch.setPlaceholder("Filter");
    }

    private void initIcoSearch()
    {
        Grid.Column<TableClientItem> column = clientTable.getColumnByKey("icoColumn");

        TextField icoSearch = new TextField();
        icoSearch.addValueChangeListener(event -> {
            this.clientController.getTableFilter().setIco(event.getValue());
            this.clientController.filterDataInTable();
        });
        icoSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(icoSearch);
        icoSearch.setSizeFull();
        icoSearch.setPlaceholder("Filter");
    }


    public void showSuccessOperationClientNotification(String successMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showSuccessMessage(successMessage,5000);
    }

    public void showErrorMessage(String errorMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorMessage);
    }




    public Grid<TableClientItem> getClientTable()
    {
        return this.clientTable;
    }



    /**
     * This model binds properties between ClientMainView and client-main-view
     */
    public interface ClientMainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
