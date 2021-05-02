package sk.stu.fei.uim.bp.application.backend.contracts.web;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import sk.stu.fei.uim.bp.application.backend.contracts.domain.ContractDocument;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.LifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.NonLifeInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.domain.VehicleInsurance;
import sk.stu.fei.uim.bp.application.backend.contracts.service.ContractService;
import sk.stu.fei.uim.bp.application.backend.contracts.service.implementation.ContractServiceImpl;
import sk.stu.fei.uim.bp.application.backend.contracts.web.controllers.ContractController;
import sk.stu.fei.uim.bp.application.backend.contracts.web.controllers.LifeInsuranceController;
import sk.stu.fei.uim.bp.application.backend.contracts.web.controllers.NonLifeInsuranceController;
import sk.stu.fei.uim.bp.application.backend.contracts.web.controllers.VehicleInsuranceController;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.LifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.NonLifeInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.contracts.web.editors.VehicleInsuranceEditor;
import sk.stu.fei.uim.bp.application.backend.contracts.web.table.TableContractItem;
import sk.stu.fei.uim.bp.application.ui.NotificationProvider;
import java.time.LocalDate;
import java.util.Optional;


@Tag("contract-main-view")
@JsModule("./views/contracts/contract-main-view.js")
@PageTitle(value = "Zmluvy")
@Route(value = "zmluvy", layout = MainView.class)
public class ContractMainView extends PolymerTemplate<ContractMainView.ContractMainViewModel> {

    private final ContractService contractService;
    private final ContractConverter contractConverter;
    private final ObjectId currentAgent = new ObjectId("601b6300dbf3207494372a20");

    @Id("addLifeInsurance")
    private Button addLifeInsurance;
    @Id("addNonLifeInsurance")
    private Button addNonLifeInsurance;
    @Id("addVehicleInsurance")
    private Button addVehicleInsurance;
    @Id("contractTable")
    private Grid<TableContractItem> contractTable;

    private Dialog window;

    private final LifeInsuranceEditor lifeInsuranceEditor;
    private final NonLifeInsuranceEditor nonLifeInsuranceEditor;
    private final VehicleInsuranceEditor vehicleInsuranceEditor;


    private final ContractController contractController;
    private final LifeInsuranceController lifeInsuranceController;
    private final NonLifeInsuranceController nonLifeInsuranceController;
    private final VehicleInsuranceController vehicleInsuranceController;


    @Autowired
    public ContractMainView(ContractServiceImpl contractService,
                            ContractConverter contractConverter,
                            LifeInsuranceEditor lifeInsuranceEditor,
                            NonLifeInsuranceEditor nonLifeInsuranceEditor,
                            VehicleInsuranceEditor vehicleInsuranceEditor)
    {
       this.contractService = contractService;
       this.contractConverter = contractConverter;

        initColumns();
        HeaderRow searchRow = this.contractTable.appendHeaderRow();
        initSearchRow(searchRow);
        initWindow();

        this.lifeInsuranceEditor = lifeInsuranceEditor;
        this.nonLifeInsuranceEditor = nonLifeInsuranceEditor;
        this.vehicleInsuranceEditor = vehicleInsuranceEditor;

       this.contractController = new ContractController(this,currentAgent,contractService,contractConverter);

       this.lifeInsuranceController = new LifeInsuranceController(this,
               this.contractConverter,contractService,this.currentAgent);

       this.nonLifeInsuranceController = new NonLifeInsuranceController(this,this.contractConverter,contractService,this.currentAgent);

        this.vehicleInsuranceController = new VehicleInsuranceController(this,this.contractConverter,contractService,this.currentAgent);




       setActionOnAddButton();

       this.contractTable.addItemDoubleClickListener(item -> {
           TableContractItem contractItem = item.getItem();
           this.doUpdateContract(contractItem);
       });


    }


    private void initColumns()
    {
        Grid.Column<TableContractItem> contractNumberColumn = this.contractTable.addColumn(TableContractItem::getContractNumber);
        contractNumberColumn.setAutoWidth(true);
        contractNumberColumn.setHeader("Číslo zmluvy");
        contractNumberColumn.setKey("contractNumberColumn");
        contractNumberColumn.setId("contractNumberColumn");

        Grid.Column<TableContractItem> contractOwnerColumn = this.contractTable.addColumn(TableContractItem::getOwnerFullName);
        contractOwnerColumn.setAutoWidth(true);
        contractOwnerColumn.setHeader("Poistiteľ");
        contractOwnerColumn.setKey("contractOwnerColumn");
        contractOwnerColumn.setId("contractOwnerColumn");

        Grid.Column<TableContractItem> contractInsuredColumn = this.contractTable.addColumn(TableContractItem::getInsuredFullName);
        contractInsuredColumn.setAutoWidth(true);
        contractInsuredColumn.setHeader("Poistený");
        contractInsuredColumn.setKey("contractInsuredColumn");
        contractInsuredColumn.setId("contractInsuredColumn");

        Grid.Column<TableContractItem> contractCompanyNameColumn = this.contractTable.addColumn(TableContractItem::getCompanyName);
        contractCompanyNameColumn.setAutoWidth(true);
        contractCompanyNameColumn.setHeader("Spoločnosť");
        contractCompanyNameColumn.setKey("contractCompanyNameColumn");
        contractCompanyNameColumn.setId("contractCompanyNameColumn");

        Grid.Column<TableContractItem> contractProductNameColumn = this.contractTable.addColumn(TableContractItem::getProductName);
        contractProductNameColumn.setAutoWidth(true);
        contractProductNameColumn.setHeader("Produkt");
        contractProductNameColumn.setKey("contractProductNameColumn");
        contractProductNameColumn.setId("contractProductNameColumn");

        Grid.Column<TableContractItem> contractAnniversaryDateColumn = this.contractTable.addColumn(TableContractItem::getAnniversaryDate);
        contractAnniversaryDateColumn.setAutoWidth(true);
        contractAnniversaryDateColumn.setHeader("Výročie zmluvy");
        contractAnniversaryDateColumn.setKey("contractAnniversaryDateColumn");
        contractAnniversaryDateColumn.setId("contractAnniversaryDateColumn");

        Grid.Column<TableContractItem> contractnumberOfVehicleColumn = this.contractTable.addColumn(TableContractItem::getNumberOfVehicle);
        contractnumberOfVehicleColumn.setAutoWidth(true);
        contractnumberOfVehicleColumn.setHeader("ŠPZ");
        contractnumberOfVehicleColumn.setKey("contractnumberOfVehicleColumn");
        contractnumberOfVehicleColumn.setId("contractnumberOfVehicleColumn");

    }

    private void initSearchRow(HeaderRow searchRow)
    {
        initContractNumberSearch(searchRow);
        initContractOwnerSearch(searchRow);
        initContractInsuredSearch(searchRow);
        initContractCompanySearch(searchRow);
        initContractProductSearch(searchRow);
        initContractAnniversaryDateSearch(searchRow);
        initContractNumberOfVehicleSearch(searchRow);
    }

    private void initContractNumberSearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractNumberColumn");

        TextField contractNumberSearch = new TextField();
        contractNumberSearch.addValueChangeListener(event -> {
            this.contractController.getContractFilter().setContractNumber(event.getValue());
            this.contractController.filterDataInTable();
        });
        contractNumberSearch.setValueChangeMode(ValueChangeMode.EAGER);


        headerRow.getCell(column).setComponent(contractNumberSearch);
        contractNumberSearch.setSizeFull();
        contractNumberSearch.setPlaceholder("Filter");

    }

    private void initContractOwnerSearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractOwnerColumn");

        TextField contractOwnerSearch = new TextField();
        contractOwnerSearch.addValueChangeListener(event -> {
            this.contractController.getContractFilter().setOwnerFullName(event.getValue());
            this.contractController.filterDataInTable();
        });
        contractOwnerSearch.setValueChangeMode(ValueChangeMode.EAGER);


        headerRow.getCell(column).setComponent(contractOwnerSearch);
        contractOwnerSearch.setSizeFull();
        contractOwnerSearch.setPlaceholder("Filter");
    }

    private void initContractInsuredSearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractInsuredColumn");

        TextField contractInsuredSearch = new TextField();
        contractInsuredSearch.addValueChangeListener(event -> {
            this.contractController.getContractFilter().setInsuredFullName(event.getValue());
            this.contractController.filterDataInTable();
        });
        contractInsuredSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(contractInsuredSearch);
        contractInsuredSearch.setSizeFull();
        contractInsuredSearch.setPlaceholder("Filter");
    }

    private void initContractCompanySearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractCompanyNameColumn");

        TextField contractCompanySearch = new TextField();
        contractCompanySearch.addValueChangeListener(event -> {
            this.contractController.getContractFilter().setCompanyName(event.getValue());
            this.contractController.filterDataInTable();
        });
        contractCompanySearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(contractCompanySearch);
        contractCompanySearch.setSizeFull();
        contractCompanySearch.setPlaceholder("Filter");
    }

    private void initContractProductSearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractProductNameColumn");

        TextField contractProductSearch = new TextField();
        contractProductSearch.addValueChangeListener(event -> {
            this.contractController.getContractFilter().setProductName(event.getValue());
            this.contractController.filterDataInTable();
        });
        contractProductSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(contractProductSearch);
        contractProductSearch.setSizeFull();
        contractProductSearch.setPlaceholder("Filter");
    }

    private void initContractAnniversaryDateSearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractAnniversaryDateColumn");

        Checkbox contractAnniversaryDateSearch = new Checkbox();
        contractAnniversaryDateSearch.setLabel("Blížiace sa výročie");
        contractAnniversaryDateSearch.addValueChangeListener(event -> {
            if(event.getValue())
            {
                this.contractController.getContractFilter().setAnniversaryDate(LocalDate.now());
            }
            else
                this.contractController.getContractFilter().setAnniversaryDate(null);
            this.contractController.filterDataInTable();
        });

        headerRow.getCell(column).setComponent(contractAnniversaryDateSearch);
        contractAnniversaryDateSearch.setSizeFull();
    }

    private void initContractNumberOfVehicleSearch(HeaderRow headerRow)
    {
        Grid.Column<TableContractItem> column = this.contractTable.getColumnByKey("contractnumberOfVehicleColumn");

        TextField contractNumberOfVehicleSearch = new TextField();
        contractNumberOfVehicleSearch.addValueChangeListener(event -> {
            this.contractController.getContractFilter().setNumberOfVehicle(event.getValue());
            this.contractController.filterDataInTable();
        });
        contractNumberOfVehicleSearch.setValueChangeMode(ValueChangeMode.EAGER);

        headerRow.getCell(column).setComponent(contractNumberOfVehicleSearch);
        contractNumberOfVehicleSearch.setSizeFull();
        contractNumberOfVehicleSearch.setPlaceholder("Filter");
    }


    private void initWindow()
    {
        this.window = new Dialog();
        this.window.setModal(true);
        this.window.setCloseOnOutsideClick(false);
    }

    private void setActionOnAddButton()
    {
        this.addLifeInsurance.addClickListener(event -> this.lifeInsuranceController.addNewLifeInsurance());

        this.addNonLifeInsurance.addClickListener(event -> this.nonLifeInsuranceController.addNewNonLifeInsurance());

        this.addVehicleInsurance.addClickListener(event -> this.vehicleInsuranceController.addNewVehicleInsurance());
    }

    public void showWindow(Component component)
    {
        this.window.add(component);
        this.window.open();
    }

    public void closeWindow()
    {
        this.window.removeAll();
        this.window.close();
    }

    public void refreshTable()
    {
        this.contractController.refreshTableData();
    }

    private void doUpdateContract(TableContractItem tableContractItem)
    {
        Optional<ContractDocument> selectedContractDocument = this.contractController.getContractDocumentById(tableContractItem.getContractId());

        if(selectedContractDocument.isPresent())
        {
            ContractDocument contractDocument = selectedContractDocument.get();
            this.doUpdate(contractDocument);
        }
        else
        {
            showErrorMessage("Životné poistenie sa nepodarilo načítať");
        }
    }


    private void doUpdate(ContractDocument contractDocument)
    {
        if(contractDocument instanceof LifeInsurance)
        {
            updateLifeInsurance((LifeInsurance) contractDocument);
        }
        else if(contractDocument instanceof VehicleInsurance)
        {
            updateVehicleInsurance((VehicleInsurance) contractDocument);
        }
        else if(contractDocument instanceof NonLifeInsurance)
        {
            updateNonLifeInsurance((NonLifeInsurance) contractDocument);
        }
    }

    private void updateLifeInsurance(LifeInsurance lifeInsurance)
    {
        this.lifeInsuranceController.updateLifeInsurance(lifeInsurance);
    }

    private void updateNonLifeInsurance(NonLifeInsurance nonLifeInsurance)
    {
        this.nonLifeInsuranceController.updateNonLifeInsurance(nonLifeInsurance);
    }

    private void updateVehicleInsurance(VehicleInsurance vehicleInsurance)
    {
        this.vehicleInsuranceController.updateVehicleInsurance(vehicleInsurance);
    }

    public void showSuccessOperationNotification(String successMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showSuccessMessage(successMessage,5000);
    }

    public void showErrorMessage(String errorMessage)
    {
        NotificationProvider notificationProvider = new NotificationProvider();
        notificationProvider.showErrorMessage(errorMessage);
    }


    public Grid<TableContractItem> getContractTable()
    {
        return this.contractTable;
    }

    public LifeInsuranceEditor getLifeInsuranceEditor()
    {
        return this.lifeInsuranceEditor;
    }

    public NonLifeInsuranceEditor getNonLifeInsuranceEditor() {
        return nonLifeInsuranceEditor;
    }

    public VehicleInsuranceEditor getVehicleInsuranceEditor() {
        return vehicleInsuranceEditor;
    }

    public interface ContractMainViewModel extends TemplateModel {

    }
}
